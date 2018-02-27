package app.service;

import app.repository.dto.*;

import java.util.List;

public interface ProjectService {

    ProjectDTO get(long id);
    byte[] getProjectAttachment(long id);
    ProjectSearchResponseDTO getProjects(ProjectSearchRequestDTO request);
    List<ProjectAttachmentTypeDTO> getAttachmentTypes();
    long add(ProjectDTO project);
    void update(ProjectDTO project);
    void post(long id);
    void remove(long id);
    long addAttachment(long projectId, byte[] value, long attachmentTypeId);
    void removeAttachment(long id);
    long comment(ProjectCommentDTO comment);
    void removeComment(long id);
    ProjectCommentSearchResponseDTO getComments(ProjectCommentSearchRequestDTO request);
    void like(long id);
    void dislike(long id);
    void unlike(long id);
}
