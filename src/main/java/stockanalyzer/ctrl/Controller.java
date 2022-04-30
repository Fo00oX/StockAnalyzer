package stockanalyzer.ctrl;

import stockanalyzer.ui.UserInterface;
import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.Result;
import yahooApi.beans.YahooResponse;

import java.util.*;

public class Controller {

    public void process ( String ticker ) {
        System.out.println ( "Start process" );

        try {
            QuoteResponse quoteResponse = (QuoteResponse) getData ( ticker );

            System.out.println ( "Highest Ask: " + System.lineSeparator ( )
                    + quoteResponse.getResult ( )
                    .stream ( )
                    .mapToDouble ( Result::getAsk )
                    .max ( ).orElseThrow ( ( ) -> new YahooException ( ("We are sorry, we could not find the highest Ask Price for this choice") ) ) );
            System.out.println ( "Average Ask: " + System.lineSeparator ( )
                    + quoteResponse.getResult ( )
                    .stream ( ).mapToDouble ( Result::getAsk )
                    .average ( ).orElseThrow ( ( ) -> new YahooException ( "We are sorry, we could not find the average Ask Price for this choice." ) ) );
            System.out.println ( "Data: " + System.lineSeparator ( )
                    + quoteResponse.getResult ( )
                    .stream ( ).mapToDouble ( Result::getAsk ).count ( ) );
        } catch ( YahooException e ) {
            UserInterface.print ( "An Error acquired");
        }
    }

    public Object getData ( String searchString ) {

            List<String> tickers = Arrays.asList ( searchString.split ( "," ) );

            YahooResponse response = YahooFinance.getCurrentData ( tickers );

            return response.getQuoteResponse ( );

    }

    public void closeConnection ( ) {

    }
}