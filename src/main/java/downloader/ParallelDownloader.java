package downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader {

    @Override
    public int process(List<String> ticker) {

        int savedTickers = 0;
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
                    savedTickers++;
                }

            } catch ( InterruptedException | ExecutionException e) {
                System.out.println ("here is no Data available for Parallel downloader." );
            }
        }
        return savedTickers;
    }
}