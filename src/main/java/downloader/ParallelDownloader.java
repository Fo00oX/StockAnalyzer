package downloader;

import stockanalyzer.ui.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader {

    @Override
    public int process(List<String> ticker)   {

        int count = 0;
        int threads = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        List<Future<String>> parallelDownload = new ArrayList<>();

        for(String tickers: ticker){
            parallelDownload.add(executorService.submit(()->saveJson2File(tickers)));

        }

        for(Future<String> futures : parallelDownload){
            try {
                String tickers = futures.get();

                if(tickers != null){
                    count++;
                }
            } catch ( InterruptedException | ExecutionException  e) {
                UserInterface.printMessage( System.lineSeparator () +
                        "=============================================================================================================================" +
                        System.lineSeparator () +
                        "Here is no Data available for Parallel downloader."+
                        System.lineSeparator () +
                        "=============================================================================================================================");
            }
        }
        return count;
    }
}