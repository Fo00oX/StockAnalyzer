package stockanalyzer.ctrl;

public class YahooException extends Exception {

    public YahooException ( String errorMessage ) {
        super(errorMessage);
    }
}