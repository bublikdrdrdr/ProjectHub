package app.repository.dao;

import app.repository.entity.Project;
import app.repository.entity.ProjectComment;
import app.repository.etc.CommentSearchParams;
import app.repository.etc.SearchParams;

import java.util.List;

public interface CommentRepository extends CrudRepository<ProjectComment>, Searchable<ProjectComment, CommentSearchParams> {
}
