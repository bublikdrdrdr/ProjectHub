package app.repository.dao;

import app.repository.entity.Project;
import app.repository.etc.ProjectSearchParams;

import java.util.List;

public interface ProjectRepository {

    Project getProject(long id);
    long saveProject(Project project);
    List<Project> search(ProjectSearchParams searchParams);
    long count(ProjectSearchParams searchParams);
    void remove(Project project);
}
