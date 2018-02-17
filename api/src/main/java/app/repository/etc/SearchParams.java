package app.repository.etc;


import app.util.PropertiesLoader;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

/**
 * Created by Bublik on 23-Dec-17.
 */
@PropertySource("service.properties")
public class SearchParams {

    protected String sort;
    public boolean desc;
    public int first = 0;

    public int count = Integer.parseInt(PropertiesLoader.getProperty("search.default_count"));

    protected SearchParams(String sort, Boolean desc, Integer first, Integer count) {
        this.sort = sort;
        this.desc = nullToDefault(desc, false);
        if (first!=null && first>=0) this.first = first;
        int maxCount = PropertiesLoader.getProperty("search.max_count", 200);
        if (count!=null) this.count = Math.max(Math.min(count, maxCount), 0);
    }

    protected <T> T nullToDefault(T value, @NotNull T defaultValue){
        return (value==null?defaultValue:value);
    }
}
