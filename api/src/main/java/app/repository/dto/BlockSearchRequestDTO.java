package app.repository.dto;

public class BlockSearchRequestDTO extends PaginationDTO {

    public Long admin;
    public Long user;
    public Boolean active_only;

    public BlockSearchRequestDTO() {
    }

    public BlockSearchRequestDTO(Integer sort, Boolean desc, Integer first, Integer count, Long admin, Long user, Boolean active_only) {
        super(sort, desc, first, count);
        this.admin = admin;
        this.user = user;
        this.active_only = active_only;
    }
}
