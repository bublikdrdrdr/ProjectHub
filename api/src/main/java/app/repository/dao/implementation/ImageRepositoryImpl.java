package app.repository.dao.implementation;

import app.repository.dao.CrudRepository;
import app.repository.dao.ImageRepository;
import app.repository.entity.Image;
import app.repository.entity.User;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class ImageRepositoryImpl extends AbstractRepository implements ImageRepository {

    @Override
    public Image get(long id) {
        return getEntity(Image.class, id, entity -> {
            Hibernate.initialize(entity.getFile());
        });
    }

    @Override
    public long save(Image image) {
        saveEntity(image);
        return image.getId();
    }

    @Override
    public void remove(Image image) {
        removeEntity(image);
    }

    @Override
    public Image getUserImage(User user, boolean includeValue) {
        try {
            wrapper.getSession();
            if (includeValue) Hibernate.initialize(user.getImage());
            return user.getImage();
        } finally {
            wrapper.closeSession();
        }
    }
}
