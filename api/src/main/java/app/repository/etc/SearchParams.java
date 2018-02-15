package app.repository.etc;


import app.util.PropertiesLoader;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

/**
 * Created by Bublik on 23-Dec-17.
 */
@PropertySource("service.properties")
public class SearchParams {

    protected String sort;
    public boolean desc = false;
    public int first = 0;

    public int count = Integer.parseInt(PropertiesLoader.getProperty("search.default_count"));

    protected SearchParams(String sort, Boolean desc, Integer first, Integer count) {
        this.sort = sort;
        if (desc!=null) this.desc = desc;
        if (first!=null && first>=0) this.first = first;
        int maxCount = PropertiesLoader.getProperty("search.max_count", 200);
        if (count!=null) this.count = Math.max(Math.min(count, maxCount), 0);
    }
}
