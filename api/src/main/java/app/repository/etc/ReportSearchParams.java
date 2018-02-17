package app.repository.etc;

import app.repository.entity.Report;
import app.repository.entity.User;
import com.sun.istack.internal.Nullable;

import javax.validation.constraints.Null;
import java.sql.Timestamp;

public class ReportSearchParams extends SearchParams {

    public enum Sort {NONE, SENT}

    public User admin;
    public User sender;
    public Report.ReportType reportType;
    public String message;
    public Timestamp sentBefore;
    public Timestamp sentAfter;
    public boolean activeOnly = false;

    public ReportSearchParams(Sort sort, Boolean desc, Integer first, Integer count, User admin, User sender, Report.ReportType reportType, String message, Timestamp sentBefore, Timestamp sentAfter, Boolean activeOnly) {
        super(sort.toString(), desc, first, count);
        this.admin = admin;
        this.sender = sender;
        this.reportType = reportType;
        this.message = message;
        this.sentBefore = sentBefore;
        this.sentAfter = sentAfter;
        if (activeOnly!=null) this.activeOnly = activeOnly;
    }

    public Sort getSort(){
        return Sort.valueOf(sort);
    }
}
