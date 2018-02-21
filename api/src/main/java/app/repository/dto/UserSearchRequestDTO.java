package app.repository.dto;

import app.repository.etc.UserSearchParams;

public class UserSearchRequestDTO extends PaginationDTO {

    public String email;
    public String username;
    public String name;
    public Boolean online;
    public Boolean exact;

    public UserSearchRequestDTO() {
    }

    public UserSearchRequestDTO(Integer sort, Boolean desc, Integer first, Integer count, String email, String username, String name, Boolean online, Boolean exact) {
        super(sort, desc, first, count);
        this.email = email;
        this.username = username;
        this.name = name;
        this.online = online;
        this.exact = exact;
    }

    public UserSearchParams.Sort getSortValue(){
        try{
            return UserSearchParams.sortValues[sort];
        } catch (Exception e){
            return UserSearchParams.Sort.NONE;
        }
    }

    public UserSearchParams getSearchParams(){
        return new UserSearchParams(exact, getSortValue(), desc, first, count, email, username, name, online);
    }
}
