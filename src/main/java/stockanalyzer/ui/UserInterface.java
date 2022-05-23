package stockanalyzer.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import downloader.ParallelDownloader;
import downloader.SequentialDownloader;
import stockanalyzer.ctrl.Controller;
import stockanalyzer.ctrl.YahooException;

public class UserInterface {

    private Controller ctrl = new Controller();

    public static void printMessage ( String errorMessage ) {
        System.out.println (errorMessage );
    }

    public void getDataFromCtrl1()   {
        ctrl.process ( "AAPL,TSLA,GOOG" );
    }

    public void getDataFromCtrl2() {
        ctrl.process ( "OMV.VI,EBS.VI,DOC.VI,SBO.VI,RBI.VI,VIG.VI,TKA.VI,VOE.VI,FACC.VI,ANDR.VI,VER.VI," +
                "WIE.VI,CAI.VI,BG.VI,POST.VI,LNZ.VI,UQA.VI,SPI.VI,ATS.VI,IIA.VI");
    }

    public void getDataFromCtrl3() {

        ctrl.process ( "GOOG" );
    }

    private void getDataFomDownloader () {

        long startTime = 0;
        long endTime = 0;
        long runTimeSequential = 0;
        long runTimeParallel = 0;

        SequentialDownloader sequentialDownloader = new SequentialDownloader();
        ParallelDownloader parallelDownloader = new ParallelDownloader();

        List<String> tickers = Arrays.asList("FB","TSLA","MSFT","NFLX","NOK","GOOG","GME","AAPL","BTC-USD","DOGE-USD","ETH-USD",
                "OMV.VI","EBS.VI","DOC.VI","SBO.VI","RBI.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI","WIE.VI","CAI.VI","BG.VI",
                "POST.VI","LNZ.VI","UQA.VI","SPI.VI");

        try{

            System.out.println("========== Sequential Download Start ========== ");
            startTime = System.currentTimeMillis();
            ctrl.downloadTickers(tickers, sequentialDownloader);

            endTime = System.currentTimeMillis();
            runTimeSequential = endTime - startTime;
            System.out.println("========== Sequential Download End ========== ");

            System.out.println("========== Parallel Download Start ========== ");
            startTime = System.currentTimeMillis();
            ctrl.downloadTickers(tickers, parallelDownloader);

            endTime = System.currentTimeMillis();
            runTimeParallel = endTime - startTime;
            System.out.println("========== Parallel Download End ========== ");

        }
        catch( YahooException | JsonProcessingException e){
            System.out.println("We are sorry here is no Connection to the Downloader.");
        }

        System.out.println ("\n" + "Parallel Download needed: " + runTimeParallel + " ms" );
        System.out.println ("Sequential Download needed: " + runTimeSequential + " ms" );
        System.out.println ("\n" + "The difference between the Download Processes is: " + Math.abs ( runTimeParallel - runTimeSequential ) + " ms");

    }

    public void start()   {

        Menu<Runnable> menu = new Menu<> ( "User Interface" );
        menu.setTitel ( "Wählen Sie aus:" );
        menu.insert ( "a" , "AAPL,TSLA,GOOG" , this::getDataFromCtrl1 );
        menu.insert ( "b" , "Data from 20 sources" , this::getDataFromCtrl2 );
        menu.insert ( "c" , "GOOG" , this::getDataFromCtrl3 );
        menu.insert ( "q" , "Quit" , null );
        menu.insert("d","Download", this::getDataFomDownloader );
        Runnable choice;

        while ((choice = menu.exec()) != null)
            choice.run();

        ctrl.closeConnection();
        System.out.println("Program finished");
    }

    protected String readLine()   {
        String value = "\0";
        BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = inReader.readLine();
        } catch ( IOException e ) {
            System.out.println ("Here is nothing to read." );
        }
        return value.trim();
    }

    protected Double readDouble(int lowerlimit, int upperlimit)
    {
        Double number = null;
        while(number == null) {
            String str = this.readLine();
            try {
                number = Double.parseDouble(str);
            }catch(NumberFormatException e) {
                System.out.println("Please enter a valid number:");
                continue;
            }
            if(number<lowerlimit) {
                System.out.println("Please enter a higher number:");
                number=null;
            }else if(number>upperlimit) {
                System.out.println("Please enter a lower number:");
                number=null;
            }
        }
        return number;
    }
}