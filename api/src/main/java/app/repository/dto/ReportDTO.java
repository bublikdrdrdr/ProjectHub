package app.repository.dto;

import app.repository.entity.Report;
import app.repository.etc.UserSearchParams;
import com.fasterxml.jackson.annotation.JsonInclude;

import static app.util.ServiceUtils.now;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDTO {

    public Long id;
    public Integer type;
    public Long sender;
    public String message;
    public String url;
    public Long sent;
    public Long admin;
    public Boolean closed;

    public ReportDTO() {
    }

    public ReportDTO(Long id, Integer type, Long sender, String message, String url, Long sent, Long admin, Boolean closed) {
        this.id = id;
        this.type = type;
        this.sender = sender;
        this.message = message;
        this.sent = sent;
        this.url = url;
        this.admin = admin;
        this.closed = closed;
    }

    public Report.ReportType getReportTypeValue(){
        try{
            return Report.reportTypeValues[type];
        } catch (Exception e){
            return Report.ReportType.BUG;
        }
    }
}
