package app.repository.dto;

import app.repository.entity.User;
import app.repository.etc.BookmarkSearchParams;
import app.repository.etc.UserSearchParams;

public class BookmarkSearchRequestDTO extends PaginationDTO {

    public String name;
    public Boolean online;

    public BookmarkSearchRequestDTO() {
    }

    public BookmarkSearchRequestDTO(Integer sort, Boolean desc, Integer first, Integer count, String name, Boolean online) {
        super(sort, desc, first, count);
        this.name = name;
        this.online = online;
    }

    public BookmarkSearchParams.Sort getSortValue(){
        try{
            return BookmarkSearchParams.sortValues[sort];
        } catch (Exception e){
            return BookmarkSearchParams.Sort.NONE;
        }
    }

    public BookmarkSearchParams getSearchParams(User user){
        return new BookmarkSearchParams(getSortValue(), user, name, desc, first, count, online);
    }
}
