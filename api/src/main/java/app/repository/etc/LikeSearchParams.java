package app.repository.etc;

import app.repository.entity.LikedProject;
import app.repository.entity.Project;
import app.repository.entity.User;

public class LikeSearchParams extends SearchParams {

    public Project project;
    public User user;
    public LikedProject.MarkType markType;

    public LikeSearchParams(String sort, Boolean desc, Integer first, Integer count, Project project, User user, LikedProject.MarkType markType) {
        super(sort, desc, first, count);
        this.project = project;
        this.user = user;
        this.markType = markType;
    }
}
