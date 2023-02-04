package com.example.redsocks.services.Impl;

import com.example.redsocks.model.Socks;
import com.example.redsocks.model.category.ColorOfSocks;
import com.example.redsocks.model.category.ShoeSize;
import com.example.redsocks.services.SocksFileService;
import com.example.redsocks.services.SocksService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SocksServiceImpl
        implements SocksService, Serializable {

    @Value("${pathToDataFile}")
    private String pathToDataFile;
    @Value("${nameOfSocksDataFile}")
    private String nameOfSocksDataFile;
    private final SocksFileService socksFileService;

    public SocksServiceImpl(SocksFileService socksFileService) {
        this.socksFileService = socksFileService;
    }

    private Map<Socks, Integer> socksWarehouse = new LinkedHashMap<>();

    @PostConstruct
    private void maker() {
        checkOrFillFile();
        readFromFile();
    }

    @Override
    public void addSocks(Socks socks, int quantity) {
        if (socksWarehouse.containsKey(socks)) {
            socksWarehouse.replace(socks, socksWarehouse.get(socks) + quantity);
            writeToFile();
            return;
        }
        socksWarehouse.put(socks, quantity);
        writeToFile();
    }

    @Override
    public boolean releaseOfSocks(Socks socks, int quantity) {
        if (socksWarehouse.get(socks) >= quantity && quantity > 0) {
            socksWarehouse.replace(socks, socksWarehouse.get(socks) - quantity);
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
    public boolean deleteSocks(Socks socks, int quantity) {
        if (socksWarehouse.get(socks) >= quantity && quantity > 0) {
            socksWarehouse.replace(socks, socksWarehouse.get(socks) - quantity);
            return true;
        }
        return false;
    }

    private void writeToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(socksWarehouse);
            socksFileService.writeToFile(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void readFromFile() {
        String json = socksFileService.readFromFile();
        try {
            socksWarehouse = new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Socks, Integer>>() {
            });
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private void checkOrFillFile() {
        if (!Files.exists(Path.of(pathToDataFile, nameOfSocksDataFile))) {
            socksWarehouse.put(new Socks(), 0);
            writeToFile();
        }
    }
}
