package app.repository.dao;

import app.repository.entity.User;
import app.repository.entity.UserBookmark;
import app.repository.etc.BookmarkSearchParams;

import java.util.List;

public interface BookmarkRepository extends CrudRepository<UserBookmark>, Searchable<UserBookmark, BookmarkSearchParams> {
}
