package app.repository.etc;

public class ProjectSearchParams extends SearchParams{

    public static final Sort[] sortValues = Sort.values();

    public enum Sort {NONE, CREATED, POSTED, SUBJECT, LIKES}

    public Long authorId;
    public String subject;
    public String content;
    public boolean exact;

    public ProjectSearchParams(Boolean exact, Sort sort, Boolean desc, Integer first, Integer count, Long authorId, String subject, String content) {
        super(sort.toString(), desc, first, count);
        this.exact = nullToDefault(exact, false);
        this.authorId = authorId;
        this.subject = subject;
        this.content = content;
    }

    public Sort getSort(){
        return Sort.valueOf(sort);
    }

}
