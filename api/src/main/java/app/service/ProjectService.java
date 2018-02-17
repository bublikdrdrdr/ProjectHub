package app.service;

import app.repository.entity.Project;
import app.repository.entity.ProjectAttachment;
import app.repository.entity.ProjectComment;
import app.repository.etc.ProjectSearchParams;

import java.util.List;

public interface ProjectService {

    Project get(long id);
    long add(Project project);
    void update(Project project);
    void post(long id);
    void remove(long id);
    long addAttachment(ProjectAttachment attachment);
    void removeAttachment(long id);
    long comment(ProjectComment projectComment);
    void removeComment(long id);
    void like(long id);
    void dislike(long id);
    void unlike(long id);
    List<Project> search(ProjectSearchParams searchParams);
    long searchCount(ProjectSearchParams searchParams);

}
