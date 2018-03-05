package app.repository.dao;

import app.repository.entity.Image;
import app.repository.entity.User;

public interface ImageRepository extends CrudRepository<Image> {

    Image getUserImage(User user, boolean includeValue);

}
