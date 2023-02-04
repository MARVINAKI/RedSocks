package com.example.redsocks.services;

import com.example.redsocks.model.Socks;
import com.example.redsocks.model.category.ColorOfSocks;
import com.example.redsocks.model.category.ShoeSize;

import java.util.HashMap;
import java.util.Map;

public interface SocksService {

    void addSocks(Socks socks, int quantity); //приход товара

    boolean releaseOfSocks(Socks socks, int quantity); //расход товара

    int getInfoOfSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonMin, int cottonMax);

    Map<Socks, Integer> getInfoOfAllSocks(); //под итог

    boolean deleteSocks(Socks socks, int quantity); //удаление со склада
}
