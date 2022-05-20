package stockanalyzer.ctrl;

import downloader.Downloader;
import stockanalyzer.ui.UserInterface;
import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.Result;

import java.util.*;

public class Controller {

    public void process ( String ticker )   {
        System.out.println ( "Start process" );

        try {
            QuoteResponse quoteResponse = getData ( ticker );

            System.out.println ( "Highest Ask: " + System.lineSeparator ( )
                    + quoteResponse.getResult ( )
                    .stream ( )
                    .mapToDouble ( Result::getAsk )
                    .max ( )
                    .orElseThrow ( ( ) -> new YahooException ( ("We are sorry, we could not find the highest Ask Price for this choice") ) ) );
            System.out.println ( "Average Ask: " + System.lineSeparator ( )
                    + quoteResponse.getResult ( )
                    .stream ( ).mapToDouble ( Result::getAsk )
                    .average ( ).orElseThrow ( ( ) -> new YahooException ( "We are sorry, we could not find the average Ask Price for this choice." ) ) );
            System.out.println ( "Data: " + System.lineSeparator ( )
                    + quoteResponse.getResult ( )
                    .stream ( ).mapToDouble ( Result::getAsk ).count ( ) );

        } catch ( YahooException | NullPointerException e ) {
            System.out.println ("We could not find a Quote" );
        }
        new UserInterface ( );
    }

    public QuoteResponse getData ( String searchString ) throws YahooException {


        List<String> tickers = Arrays.asList ( searchString.split ( "," ) );

        YahooFinance yahooFinance = new YahooFinance ( );

        if (yahooFinance.getCurrentData ( tickers ).getQuoteResponse ( ) == null) {
            System.out.println ( "error" );
        } else

            return yahooFinance.getCurrentData ( tickers ).getQuoteResponse ( );

        return null;
    }

    public void downloadTickers(List<String> ticker, Downloader downloader) throws YahooException {
        downloader.process(ticker);
    }

    public void closeConnection ( ) {

    }
}