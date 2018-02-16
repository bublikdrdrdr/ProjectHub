package app.repository.dao;

import app.repository.entity.Project;
import app.repository.etc.ProjectSearchParams;

import java.util.List;

public interface ProjectRepository {

    Project get(long id);
    long save(Project project);
    List<Project> search(ProjectSearchParams searchParams);
    long count(ProjectSearchParams searchParams);
    void remove(Project project);
}
