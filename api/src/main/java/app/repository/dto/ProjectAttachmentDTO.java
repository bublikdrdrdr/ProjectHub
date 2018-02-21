package app.repository.dto;

public class ProjectAttachmentDTO {

    public Long id;
    public Long project_id;
    public Long attachment_type;

    public ProjectAttachmentDTO() {
    }

    public ProjectAttachmentDTO(Long id, Long project_id, Long attachment_type) {
        this.id = id;
        this.project_id = project_id;
        this.attachment_type = attachment_type;
    }
}