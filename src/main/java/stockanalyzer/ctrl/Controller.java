package stockanalyzer.ctrl;

import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;

import java.util.List;

public class Controller {

    public void process( String ticker ) throws NullPointerException {

        System.out.println ( "Start process" );

        try {
            QuoteResponse response = (QuoteResponse) getData ( ticker );
            long count = response.getResult ( ).size ( );

            response.getResult ( ).forEach (
                    quote -> System.out.println (
                            System.lineSeparator ( )
                                    + "Current BID:"
                                    + System.lineSeparator ( )
                                    + quote.getAsk ( ) + " " + quote.getCurrency ( ) + System.lineSeparator ( )
                                    + System.lineSeparator ( )
                                    + "Highest Price last 52 Weeks" + System.lineSeparator ( )
                                    + quote.getFiftyTwoWeekHigh ( ) + " " + quote.getCurrency ( ) + System.lineSeparator ( )
                                    + System.lineSeparator ( )
                                    + "Average Price last 52 Weeks" + System.lineSeparator ( )
                                    + quote.getFiftyDayAverage ( ) + " " + quote.getCurrency ( ) + System.lineSeparator ( )
                                    + System.lineSeparator ( )
                                    + "Number of counted Data" + System.lineSeparator ( )
                                    + count));


        } catch ( NullPointerException | ExceptionController e ) {
            System.out.println ( e.getMessage ( ) );
        }
    }

    public Object getData( String searchString ) throws ExceptionController {

        List<String> searchStrings = List.of ( searchString );
        try {
            YahooResponse response = YahooFinance.getCurrentData ( searchStrings );
            return response.getQuoteResponse ( );
        } catch ( NullPointerException | ExceptionController e ) {
            System.out.println ( "We are sorry this happened. "
                    + YahooFinance.URL_YAHOO
                    + "is not responding." );
        }
        return null;
    }

    public void closeConnection( ) {

    }
}