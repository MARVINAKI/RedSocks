package com.example.redsocks.services.Impl;

import com.example.redsocks.model.Socks;
import com.example.redsocks.model.category.ColorOfSocks;
import com.example.redsocks.model.category.ShoeSize;
import com.example.redsocks.services.SocksService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class SocksServiceImpl
        implements SocksService {

//    @Value("${src/main/resources/MyFiles}")
//    private String pathToDataFile;
//    @Value("${socks.json}")
//    private String nameOfSocksDataFile;
    private HashMap<Socks, Integer> socksWarehouse = new LinkedHashMap<>();

    @Override
    public void addSocks(Socks socks, int stockSize) {
        if (stockSize > 0) {
            socksWarehouse.put(socks, stockSize);
        }
    }

    @Override
    public Collection<Socks> giveOutSocks(Socks socks) {
        return null;
    }

    @Override
    public Collection<Socks> getInfoOfSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonMin, int cottonMax) {
        return null;
    }

    @Override
    public Collection<Socks> getInfoOfAllSocks() {
        return null;
    }

    @Override
    public boolean deleteSocks(Socks socks) {
        return false;
    }
}
