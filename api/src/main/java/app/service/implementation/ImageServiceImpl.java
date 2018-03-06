package app.service.implementation;

import app.repository.dao.ImageRepository;
import app.repository.dao.UserRepository;
import app.repository.dto.ImageDTO;
import app.repository.entity.Image;
import app.repository.entity.User;
import app.service.AuthenticationService;
import app.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

import static app.util.ServiceUtils.now;

@Service
public class ImageServiceImpl implements ImageService {

    private AuthenticationService authenticationService;
    private UserRepository userRepository;
    private ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(AuthenticationService authenticationService, UserRepository userRepository, ImageRepository imageRepository) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public byte[] getById(long id) {
        return imageRepository.get(id).getFile();
    }

    @Override
    public ImageDTO getUserImageInfo(long userId) {
        User user = userRepository.get(userId, EnumSet.of(UserRepository.Include.IMAGES));
        return new ImageDTO(user.getImage().getId(), userId, user.getImage().getCreated().getTime());
    }

    @Override
    public long add(byte[] file) {
        User user = authenticationService.getAuthenticatedUser();
        if (user==null) throw new NullPointerException("User not found");
        remove();
        Image image = new Image(null, now(), null, file);
        long imageId = imageRepository.save(image);
        user.setImage(image);
        userRepository.save(user);
        return imageId;
        //todo: check, if it possible just to set and save user with new image object
    }

    @Override
    public void remove() {
        User user = authenticationService.getAuthenticatedUser();
        user = userRepository.get(user.getId(), EnumSet.of(UserRepository.Include.IMAGES));
        Image image = user.getImage();
        user.setImage(null);
        userRepository.save(user);
        imageRepository.remove(image);
    }
}
