package com.bjd.demo.controller;

import com.bjd.demo.dto.image.ImageDto;
import com.bjd.demo.service.image.ImageService;
import com.bjd.demo.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.bjd.demo.config.CustomSecurityContextHolder.getCurrentUserId;

@Slf4j
@Controller
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uplaodImage(@RequestParam("file") MultipartFile file) {
        Long currentUserId = getCurrentUserId();
        imageService.saveImage(file, currentUserId);
        return "redirect:/bjd/dashboard";
    }


//    @GetMapping(path = {"/get"})
//    public ResponseEntity<byte[]> getImage(Model model) {
//        Long currentUserId = getCurrentUserId();
//        ImageDto imageDto = imageService.getImageByUserId(currentUserId);
//model.addAttribute("image", ImageUtility.decompressImage(imageDto.getImage()));
//model.addAttribute("type",imageDto.getType());
//
////        return ResponseEntity
////                .ok()
////                .contentType(MediaType.valueOf(imageDto.getType()))
////                .body(ImageUtility.decompressImage(imageDto.getImage()));
//    }
}
