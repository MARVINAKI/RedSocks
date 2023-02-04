package com.example.redsocks.controllers;

import com.example.redsocks.services.SocksFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api/socks/files")
public class SocksFileController {
    private final SocksFileService socksFileService;

    public SocksFileController(SocksFileService socksFileService) {
        this.socksFileService = socksFileService;
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadDataFile() {
        File file = socksFileService.getDataFile();
        if (file.exists()) {
            try {
                InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentLength(file.length())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"socks.json\"")
                        .body(isr);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }

        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        socksFileService.cleanDataFile();
        File dataFile = socksFileService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
