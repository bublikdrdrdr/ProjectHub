package app.services;

import app.entities.db.Project;
import app.entities.db.ProjectAttachment;
import app.entities.db.ProjectComment;
import app.entities.etc.ProjectSearchParams;

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
    void unlike(long id);
    List<Project> search(ProjectSearchParams searchParams);
    long searchCount(ProjectSearchParams searchParams);

}
