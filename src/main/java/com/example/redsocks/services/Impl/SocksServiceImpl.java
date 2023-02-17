package com.example.redsocks.services.Impl;

import com.example.redsocks.model.Socks;
import com.example.redsocks.model.category.ColorOfSocks;
import com.example.redsocks.model.category.ShoeSize;
import com.example.redsocks.services.SocksFileService;
import com.example.redsocks.services.SocksService;
import com.example.redsocks.services.SocksDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SocksServiceImpl
        implements SocksService, Serializable {

    @Value("${pathToDataFile}")
    private String pathToDataFile;
    @Value("${nameOfSocksDataFile}")
    private String nameOfSocksDataFile;
    @Value("${nameOfOperationsReport}")
    private String nameOfOperationsReport;
    private final SocksFileService socksFileService;

    public SocksServiceImpl(SocksFileService socksFileService) {
        this.socksFileService = socksFileService;
    }

    @JsonProperty("socksWarehouse")
    @JsonDeserialize(keyUsing = SocksDeserializer.class)
    private Map<Socks, Integer> socksWarehouse = new LinkedHashMap<>();

    private Map<Integer, List<String>> operationsReport = new LinkedHashMap<>();

    @PostConstruct
    private void maker() {
        checkOrFillFile();
        readFromFile();
        readReportFromFile();
    }

    @Override
    public void addSocks(Socks socks, int quantity) {
        if (socksWarehouse.containsKey(socks)) {
            socksWarehouse.replace(socks, socksWarehouse.get(socks) + quantity);
            addToReport(socks, "Приход", quantity);
            writeToFile();
            socksWarehouse.remove(new Socks(ColorOfSocks.NULL, ShoeSize.NULL, 0));
            return;
        }
        socksWarehouse.put(socks, quantity);
        addToReport(socks, "Приход", quantity);
        writeToFile();
        socksWarehouse.remove(new Socks(ColorOfSocks.NULL, ShoeSize.NULL, 0));
    }

    @Override
    public boolean releaseOfSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonComposition, int quantity) {
        Socks socks = new Socks(color, shoeSize, cottonComposition);
        if (socksWarehouse.get(socks) >= quantity && quantity > 0) {
            socksWarehouse.replace(socks, socksWarehouse.get(socks) - quantity);
            addToReport(socks, "Отпуск", quantity);
            writeToFile();
            return true;
        }
        return false;
    }

    @Override
    public int getInfoOfSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonMin, int cottonMax) {
        int sum = 0;
        for (int i = cottonMin; i <= cottonMax; i++) {
            Socks socks = new Socks(color, shoeSize, i);
            sum += socksWarehouse.getOrDefault(socks, 0);
        }
        return sum;
    }

    @Override
    public Map<Socks, Integer> getInfoOfAllSocks() {
        return socksWarehouse;
    }

    @Override
    public Map<Integer, List<String>> getReport() {
        return operationsReport;
    }

    @Override
    public boolean deleteSocks(Socks socks, int quantity) {
        if (socksWarehouse.get(socks) >= quantity && quantity > 0) {
            socksWarehouse.replace(socks, socksWarehouse.get(socks) - quantity);
            addToReport(socks, "Расход", quantity);
            writeToFile();
            return true;
        }
        return false;
    }

    @Override
    public void writeToFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(socksWarehouse);
            socksFileService.writeToFile(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void writeReportToFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(operationsReport);
            socksFileService.writeReportToFile(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void readFromFile() {
        String json = socksFileService.readFromFile();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<Socks, Integer>> typeReference = new TypeReference<>() {
        };
        try {
            socksWarehouse = mapper.readValue(json, typeReference);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void readReportFromFile() {
        String json = socksFileService.readReportFromFile();
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<Integer, List<String>>> typeReference = new TypeReference<>() {
        };
        try {
            operationsReport = mapper.readValue(json, typeReference);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void addToReport(Socks socks, String typeOfOperation, int quantity) {
        List<String> report = new ArrayList<>();
        operationsReport.remove(0);
        report.add(typeOfOperation);
        report.add(String.valueOf(LocalDateTime.now()));
        report.add(String.valueOf(quantity));
        report.add(String.valueOf(socks));
        operationsReport.put(operationsReport.size() + 1, report);
        writeReportToFile();
    }

    private void checkOrFillFile() {
        if (!Files.exists(Path.of(pathToDataFile, nameOfSocksDataFile))) {
            socksWarehouse.put(new Socks(ColorOfSocks.NULL, ShoeSize.NULL, 0), 0);
            writeToFile();
        }
        if (!Files.exists(Path.of(pathToDataFile, nameOfOperationsReport))) {
            List<String> report = new ArrayList<>();
            report.add("null");
            report.add(String.valueOf(LocalDateTime.now()));
            report.add(String.valueOf(0));
            report.add(String.valueOf(new Socks(ColorOfSocks.NULL, ShoeSize.NULL, 0)));
            operationsReport.put(0, report);
            writeReportToFile();
        }
    }
}
