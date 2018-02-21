package app.repository.dto;

public class PaginationDTO {

    public Integer sort;
    public Boolean desc;
    public Integer first;
    public Integer count;

    public PaginationDTO() {
    }

    public PaginationDTO(Integer sort, Boolean desc, Integer first, Integer count) {
        this.sort = sort;
        this.desc = desc;
        this.first = first;
        this.count = count;
    }
}
