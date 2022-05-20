package stockanalyzer.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import downloader.Downloader;
import downloader.ParallelDownloader;
import downloader.SequentialDownloader;
import stockanalyzer.ctrl.Controller;
import stockanalyzer.ctrl.YahooException;

public class UserInterface {

    private Controller ctrl = new Controller();

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

    private void getDownloadData() {

        long startTime, endTime, runTime;

        Downloader sequentialDownloader = new SequentialDownloader();
        Downloader parallelDownloader = new ParallelDownloader();

        List<String> tickers = Arrays.asList("FB","TSLA","MSFT","NFLX","NOK","GOOG","GME","AAPL","BTC-USD","DOGE-USD","ETH-USD",
                "OMV.VI","EBS.VI","DOC.VI","SBO.VI","RBI.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI","WIE.VI","CAI.VI","BG.VI",
                "POST.VI","LNZ.VI","UQA.VI","SPI.VI");

        try{

            startTime = System.currentTimeMillis();
            ctrl.downloadTickers(tickers,sequentialDownloader);

            endTime = System.currentTimeMillis();
            runTime = endTime-startTime;

            System.out.print ("Sequential Download needed: " + runTime + " ms\n" );

            startTime = System.currentTimeMillis();
            ctrl.downloadTickers(tickers, parallelDownloader);

            endTime = System.currentTimeMillis();
            runTime = endTime-startTime;

            System.out.print ("Parallel Download needed: " + runTime + " ms\n" );

        }
        catch(YahooException e){
            System.out.println("Here is no Internet connection.");
        }


    }

    public void start()   {

        Menu<Runnable> menu = new Menu<> ( "User Interface" );
        menu.setTitel ( "Wählen Sie aus:" );
        menu.insert ( "a" , "AAPL,TSLA,GOOG" , this::getDataFromCtrl1 );
        menu.insert ( "b" , "Data from 20 sources" , this::getDataFromCtrl2 );
        menu.insert ( "c" , "GOOG" , this::getDataFromCtrl3 );
        menu.insert ( "q" , "Quit" , null );
        menu.insert("d","Download", this::getDownloadData);
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