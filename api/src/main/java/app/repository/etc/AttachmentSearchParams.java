package app.repository.etc;

import app.repository.entity.Project;

public class AttachmentSearchParams extends SearchParams {

    public Project project;

    public AttachmentSearchParams(String sort, Boolean desc, Integer first, Integer count, Project project) {
        super(sort, desc, first, count);
        this.project = project;
    }
}
