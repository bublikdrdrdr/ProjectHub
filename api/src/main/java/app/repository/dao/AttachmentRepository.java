package app.repository.dao;

import app.repository.entity.AttachmentType;
import app.repository.entity.ProjectAttachment;
import app.repository.etc.AttachmentSearchParams;

import java.util.List;

public interface AttachmentRepository extends CrudRepository<ProjectAttachment>, Searchable<ProjectAttachment, AttachmentSearchParams>{

    ProjectAttachment get(long id, boolean includeValue, boolean includeProject);
    AttachmentType getAttachmentType(long id);
    List<AttachmentType> getAttachmentTypes();
}
