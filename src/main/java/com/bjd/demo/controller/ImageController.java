package com.bjd.demo.controller;

import com.bjd.demo.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.bjd.demo.config.CustomSecurityContextHolder.getCurrentUserId;

@Slf4j
@Controller
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uplaodImage(@RequestParam("file") MultipartFile file) {
        Long currentUserId = getCurrentUserId();
        imageService.saveImage(file, currentUserId);
        return "redirect:/bjd/dashboard";
    }
}
