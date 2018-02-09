package app.entities.dao;

import app.entities.db.Project;
import app.entities.etc.ProjectSearchParams;

import java.util.List;

public interface ProjectRepository {

    Project getProject(long id);
    long saveProject(Project project);
    List<Project> search(ProjectSearchParams searchParams);
    long count(ProjectSearchParams searchParams);
    void remove(Project project);
}
