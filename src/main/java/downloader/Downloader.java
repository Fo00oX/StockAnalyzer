package downloader;

import stockanalyzer.ctrl.YahooException;
import stockanalyzer.ui.UserInterface;
import yahooApi.YahooFinance;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Downloader {

    public static final String DIRECTORY_DOWNLOAD = "./download/";
    private static final String JSON_EXTENSION = ".json";

    public abstract int process(List<String> urls) throws YahooException;

    public String saveJson2File(String ticker) {

        String fileName = "";
        BufferedWriter writer= null;

        try {
            YahooFinance yahooFinance = new YahooFinance();
            String json = yahooFinance.requestData(new ArrayList<>(Collections.singleton(ticker)));

            fileName = DIRECTORY_DOWNLOAD +ticker + JSON_EXTENSION;

            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(json);
            writer.close();
        } catch ( IOException e) {
            UserInterface.printMessage ("Here is no Data for Downloader");
        } finally {
            try {
                Objects.requireNonNull(writer).close();
            } catch (IOException e) {
                UserInterface.printMessage ("Here is no Data for Downloader");
            }
        }
        return fileName;
    }
}
