package com.example.redsocks.services;

import java.io.File;

public interface SocksFileService {

    void writeToFile(String json);

    String readFromFile();

    void cleanDataFile();

    File getDataFile();
}
