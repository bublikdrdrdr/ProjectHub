package app.repository.dto;

import app.repository.etc.ProjectSearchParams;

public class ProjectSearchRequestDTO extends PaginationDTO{

    public Long author;
    public String query;
    public Boolean subject_only;
    public Boolean liked;

    public ProjectSearchRequestDTO() {
    }

    public ProjectSearchRequestDTO(Integer sort, Boolean desc, Integer first, Integer count, Long author, String query, Boolean subject_only, Boolean liked) {
        super(sort, desc, first, count);
        this.author = author;
        this.query = query;
        this.subject_only = subject_only;
        this.liked = liked;
    }

    public ProjectSearchParams.Sort getSortValue(){
        try{
            return ProjectSearchParams.sortValues[sort];
        } catch (Exception e){
            return ProjectSearchParams.Sort.NONE;
        }
    }

    public ProjectSearchParams getSearchParams(){
        boolean so = subject_only==null?false:subject_only;
        return new ProjectSearchParams(false, ProjectSearchParams.Sort.NONE, desc, first, count, author, query, so?null:query);
    }
}
