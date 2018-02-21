package app.repository.dto;

import app.repository.entity.Report;
import app.repository.entity.User;
import app.repository.etc.ReportSearchParams;
import app.repository.etc.UserSearchParams;

import java.sql.Timestamp;

import static app.repository.etc.ReportSearchParams.sortValues;

public class ReportSearchRequestDTO extends PaginationDTO {

    public Long admin;
    public Long sender;
    public Integer type;
    public String message;
    public Long before;
    public Long after;
    public boolean active_only;

    public ReportSearchRequestDTO() {
    }

    public ReportSearchRequestDTO(Integer sort, Boolean desc, Integer first, Integer count, Long admin, Long sender, Integer type, String message, Long before, Long after, boolean active_only) {
        super(sort, desc, first, count);
        this.admin = admin;
        this.sender = sender;
        this.type = type;
        this.message = message;
        this.before = before;
        this.after = after;
        this.active_only = active_only;
    }

    public ReportSearchParams.Sort getSortValue(){
        try{
            return sortValues[sort];
        } catch (Exception e){
            return ReportSearchParams.Sort.NONE;
        }
    }
}
