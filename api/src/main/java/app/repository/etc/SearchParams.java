package app.repository.etc;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Bublik on 23-Dec-17.
 */
@PropertySource("service.properties")
public class SearchParams {

    //public SortBy sortBy;
    protected String sort;
    public boolean desc = false;
    public int first = 0;

    @Value("search.default_count")
    public int count;

    @Value("search.max_count")
    private int maxCount;

    public SearchParams(){

    }

    protected SearchParams(String sort, Boolean desc, Integer first, Integer count) {
        this.sort = sort;
        if (desc!=null) this.desc = desc;
        if (first!=null && first>=0) this.first = first;
        if (count!=null) this.count = Math.max(Math.min(count, maxCount), 0);
    }
}
