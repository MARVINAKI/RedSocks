package com.example.redsocks.services;


import java.io.File;

public interface SocksFileService {

    void writeToFile(String json);

    void writeReportToFile(String json);

    String readFromFile();

    String readReportFromFile();

    void cleanDataFile();

    File getDataFile();

    File getReportsFile();
}
