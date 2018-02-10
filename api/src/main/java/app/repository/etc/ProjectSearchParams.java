package app.repository.etc;

public class ProjectSearchParams extends SearchParams{

    public enum Sort {NONE, CREATED, POSTED, SUBJECT, LIKES}

    public Long authorId;
    public String subject;
    public String content;
    public boolean exact = false;

    public ProjectSearchParams(Boolean exact, String sort, Boolean desc, Integer first, Integer count, Long authorId, String subject, String content) {
        super(sort, desc, first, count);
        if (exact!=null) this.exact = exact;
        this.authorId = authorId;
        this.subject = subject;
        this.content = content;
    }

    public Sort getSort(){
        return Sort.valueOf(sort);
    }

}
