package com.example.redsocks.services;

import com.example.redsocks.model.Socks;
import com.example.redsocks.model.category.ColorOfSocks;
import com.example.redsocks.model.category.ShoeSize;

import java.util.List;
import java.util.Map;

public interface SocksService {

    void addSocks(Socks socks, int quantity);

    boolean releaseOfSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonComposition, int quantity);

    int getInfoOfSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonMin, int cottonMax);

    Map<Socks, Integer> getInfoOfAllSocks();

    Map<Integer, List<String>> getReport();

    boolean deleteSocks(Socks socks, int quantity);

    void writeToFile();

    void readFromFile();
}
