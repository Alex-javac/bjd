package com.bjd.demo.service.image;

import com.bjd.demo.dto.image.ImageDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageDto saveImage(MultipartFile file, Long currentUserId);

    String getImageByUserId(Long currentUserId);
}
