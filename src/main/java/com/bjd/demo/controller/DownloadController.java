package com.bjd.demo.controller;

import com.bjd.demo.service.route.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.bjd.demo.util.UtilConst.DEFAULT_FILE_PATH;

@Slf4j
@Controller
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {

    private final RouteService routeService;

    @GetMapping(value = "/txt")
    public void downloadTxt(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"schedule.txt\"");
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = response.getWriter()) {
            List<String[]> rows = routeService.prepareRows();
            for (String[] row : rows) {
                for (String s : row) {
                    writer.write(s);
                }
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            log.error("download", e);
            throw new RuntimeException(e);
        }
    }


    @GetMapping(value = "/csv")
    public ResponseEntity<FileSystemResource> downloadCsv() throws UnsupportedEncodingException {
        routeService.reloadFileCsv();
        File file = new File(DEFAULT_FILE_PATH, "schedule.csv");
        long fileLength = file.length();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(fileLength);
        headers.setContentDispositionFormData("attachment", "schedule.csv");
        return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/pdf")
    public ResponseEntity<FileSystemResource> downloadPdf() throws UnsupportedEncodingException {
        routeService.reloadFilePdf();
        File file = new File(DEFAULT_FILE_PATH, "schedule.pdf");
        long fileLength = file.length();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(fileLength);
        headers.setContentDispositionFormData("attachment", "schedule.pdf");
        return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
    }
}


