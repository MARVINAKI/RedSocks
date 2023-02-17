package com.example.redsocks.services.Impl;

import com.example.redsocks.services.SocksFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SocksFileServiceImpl
        implements SocksFileService {

    @Value("${pathToDataFile}")
    private String pathToDataFile;
    @Value("${nameOfSocksDataFile}")
    private String nameOfSocksDataFile;
    @Value("${nameOfOperationsReport}")
    private String nameOfOperationsReport;

    @Override
    public void writeToFile(String json) {
        try {
            Files.createDirectories(Path.of(pathToDataFile));
            Files.writeString(Path.of(pathToDataFile, nameOfSocksDataFile), json);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void writeReportToFile(String json) {
        try {
            Files.createDirectories(Path.of(pathToDataFile));
            Files.writeString(Path.of(pathToDataFile, nameOfOperationsReport), json);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(pathToDataFile, nameOfSocksDataFile));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String readReportFromFile() {
        try {
            return Files.readString(Path.of(pathToDataFile, nameOfOperationsReport));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void cleanDataFile() {
        try {
            Files.deleteIfExists(Path.of(pathToDataFile, nameOfSocksDataFile));
            Files.createFile(Path.of(pathToDataFile, nameOfSocksDataFile));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }

    @Override
    public File getDataFile() {
        return new File(pathToDataFile + "/" + nameOfSocksDataFile);
    }

    @Override
    public File getReportsFile() {
        return new File(pathToDataFile + "/" + nameOfOperationsReport);
    }
}
