package com.example.redsocks.controllers;

import com.example.redsocks.services.SocksFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;

@RestController
@Validated
@Tag(name = "Socks File operations")
@RequestMapping("/api/socks/files")
public class SocksFileController {
    private final SocksFileService socksFileService;

    public SocksFileController(SocksFileService socksFileService) {
        this.socksFileService = socksFileService;
    }

    @Operation(summary = "Скачать файл с данными о товаре")
    @GetMapping(value = "/download/socks", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @Operation(summary = "Скачать отчет о операциях")
    @GetMapping(value = "/download/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadReportsFile() {
        File file = socksFileService.getReportsFile();
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                InputStreamResource isr = new InputStreamResource(fis);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentLength(file.length())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.json\"")
                        .body(isr);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                throw new RuntimeException();
            }
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Загрузить файл с данными о товаре (в формате json)")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDataFile(@Valid @RequestParam MultipartFile file) {
        socksFileService.cleanDataFile();
        File dataFile = socksFileService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            fos.close();
            return ResponseEntity.ok().build();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
