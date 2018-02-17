package app.repository.dao;

import app.repository.entity.Project;
import app.repository.etc.ProjectSearchParams;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project>, Searchable<Project, ProjectSearchParams> {

    Project get(long id, boolean includeAttachments);
}
