package app.repository.dto;

import app.repository.entity.User;
import app.repository.etc.BlockSearchParams;

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

    public BlockSearchParams getSearchParams(User adminEntity, User userEntity){
        return new BlockSearchParams(desc, first, count, adminEntity, userEntity, active_only);
    }
}
