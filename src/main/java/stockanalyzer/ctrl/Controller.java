package stockanalyzer.ctrl;

import downloader.Downloader;
import stockanalyzer.ui.UserInterface;
import yahooApi.YahooFinance;
import yahooApi.beans.Quote;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.Result;

import java.util.*;

public class Controller {

    public void process ( String ticker ){

        System.out.println ( "Start process" );

        try {
            System.out.println(System.lineSeparator () +
                    "********************"
                    + System.lineSeparator () +
                    "Data for:" +
                    System.lineSeparator () +
                    ticker +
                    System.lineSeparator () +
                    "********************" +
                    System.lineSeparator ());

            getAverageAsk ( ticker );

            getHighestAsk ( ticker );

            getCountAnalyzedData ( ticker );


        } catch ( YahooException | NullPointerException e ) {
            UserInterface.printMessage(System.lineSeparator () +
                    "=============================================================================================================================" +
                    System.lineSeparator () +
                    "ERROR: No data was given for evaluation"+
                    System.lineSeparator () +
                    "============================================================================================================================="
                    );
        }
        new UserInterface ( );
    }

    public void getHighestAsk (String ticker) throws YahooException {

        QuoteResponse quoteResponse = getData ( ticker );

        assert quoteResponse != null;
        System.out.println ( "Highest Ask: " + System.lineSeparator ( )
                + quoteResponse.getResult ( )
                .stream ( )
                .mapToDouble ( Result::getAsk )
                .max ( ).orElseThrow ( ( ) -> new YahooException ( ("We are sorry, we could not find the highest Ask Price for this choice") ) ) );
    }

    public void getAverageAsk ( String ticker ) throws YahooException{

        QuoteResponse quoteResponse = getData ( ticker );

        assert quoteResponse != null;
        System.out.println ( "Average Ask: " + System.lineSeparator ( )
                + quoteResponse.getResult ( )
                .stream ( ).mapToDouble ( Result::getAsk )
                .average ( ).orElseThrow ( ( ) -> new YahooException ( "We are sorry, we could not find the average Ask Price for this choice." ) ) );
    }

    public void getCountAnalyzedData ( String ticker ) throws YahooException{

        QuoteResponse quoteResponse = getData ( ticker );

        assert quoteResponse != null;
        System.out.println ( "Analysed JSON Files: " + System.lineSeparator ( )
                + quoteResponse.getResult ( )
                .stream ( ).mapToDouble ( Result::getAsk ).count ( ) );
    }

    public static QuoteResponse getData (  String searchString ) throws YahooException {

        List<String> tickers = Arrays.asList ( searchString.split ( "," ) );

        YahooFinance yahooFinance = new YahooFinance ( );

        if (yahooFinance.getCurrentData ( tickers ).getQuoteResponse ( ) == null) {
            UserInterface.printMessage ( "We could nof find any available Response from YahooFinance" );
        } else

            return yahooFinance.getCurrentData ( tickers ).getQuoteResponse ( );

        return null;
    }

    public void downloadTickers(List<String> ticker, Downloader downloader) throws YahooException {

        downloader.process(ticker);
    }

    public void closeConnection ( ) {    }
}