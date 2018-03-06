package app.service;

import app.repository.dto.ImageDTO;
import app.repository.entity.Image;

public interface ImageService {

    byte[] getById(long id);
    ImageDTO getUserImageInfo(long userId);
    long add(byte[] image);
    void remove();
}
