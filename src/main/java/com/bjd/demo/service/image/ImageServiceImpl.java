package com.bjd.demo.service.image;

import com.bjd.demo.dto.image.ImageDto;
import com.bjd.demo.entity.ImageEntity;
import com.bjd.demo.entity.UserEntity;
import com.bjd.demo.mapper.ImageMapper;
import com.bjd.demo.repository.ImageRepository;
import com.bjd.demo.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Override
    @Transactional
    public ImageDto saveImage(MultipartFile file, Long currentUserId) {
        if (nonNull(file) && nonNull(currentUserId)) {
            imageRepository.deleteByUserId(currentUserId);
            ImageDto imageDto;
            try {
                ImageEntity imageEntity = imageRepository.save(ImageEntity.builder()
                        .user(new UserEntity(currentUserId))
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .image(ImageUtility.compressImage(file.getBytes())).build());
                imageDto = imageMapper.mapImageEntityToDto(imageEntity);
            } catch (IOException e) {
                log.error("ImageServiceImpl.saveImage() error while reading file ", e);
                throw new RuntimeException(e);
            }
            return imageDto;
        }
        throw new IllegalArgumentException("ImageServiceImpl.save() file can`t be null");
    }

    @Override
    public String getImageByUserId(Long currentUserId) {
        if (nonNull(currentUserId)) {
            Optional<ImageEntity> optionalImage = imageRepository.findByUserId(currentUserId);
            if(optionalImage.isPresent()) {
                byte[] bytes = ImageUtility.decompressImage(optionalImage.get().getImage());
                return Base64Utils.encodeToString(bytes);
            }else {
                return "";
            }
        }
        throw new IllegalArgumentException("ImageServiceImpl.getImageByUserId() currentUserId can`t be null");
    }
}
