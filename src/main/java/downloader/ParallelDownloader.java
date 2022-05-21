package downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader {

    @Override
    public int process(List<String> urls) {

        int counter = 0;
        int threads = Runtime.getRuntime().availableProcessors();

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        List<Future<String>> parallelDownload = new ArrayList<>();

        for(String url: urls){
            parallelDownload.add(executorService.submit(()->saveJson2File(url)));
        }

        for(Future<String> futures : parallelDownload){
            try {
                String list = futures.get();

                if(list != null){
                    counter++;
                }

            } catch ( InterruptedException | ExecutionException e) {
                System.out.println ("here is no Data available in Parallel downloader." );
            }
        }
        return counter;
    }
}