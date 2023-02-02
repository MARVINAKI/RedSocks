package com.example.redsocks.services;

import com.example.redsocks.model.Socks;
import com.example.redsocks.model.category.ColorOfSocks;
import com.example.redsocks.model.category.ShoeSize;

import java.util.Collection;
import java.util.HashMap;

public interface SocksService {

    void addSocks(Socks socks, int stockSize); //приход товара

    Collection<Socks> giveOutSocks(Socks socks); //расход товара

    Collection<Socks> getInfoOfSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonMin, int cottonMax);

    Collection<Socks> getInfoOfAllSocks(); //под итог

    boolean deleteSocks(Socks socks); //удаление со склада
}
