package app.repository.etc;

import app.repository.entity.Project;

public class CommentSearchParams extends SearchParams {

    public Project project;
    public String message;

    public CommentSearchParams(Boolean desc, Integer first, Integer count, Project project, String message) {
        super(null, desc, first, count);
        this.project = project;
        this.message = message;
    }
}
