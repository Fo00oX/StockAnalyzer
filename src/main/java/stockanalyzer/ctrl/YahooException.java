package stockanalyzer.ctrl;

import java.sql.Timestamp;

public class YahooException extends Exception {

    private final Timestamp timestamp;

    public YahooException ( String e ) {
        super ( e );
        timestamp = new Timestamp ( System.currentTimeMillis ( ) );
    }

    public YahooException ( ) {
        this ( "Any Error occurred during the fetch of the data" );
    }

    public String getMsg ( ) {
        return "YException: " + getTimestamp ( ) + " " + this.getMessage ( );
    }

    public String getTimestamp ( ) {
        return timestamp.toString ( );
    }

    @Override
    public void printStackTrace ( ) {
        System.out.println ( this.getMsg ( ) );
    }
}