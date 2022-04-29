package stockanalyzer.ctrl;

public class ExceptionController extends Exception {


    public ExceptionController( String errorMessage ) {
        super ( errorMessage );
    }

    public ExceptionController( ) {
        this ( "There is an Error accrued" );
    }

    public String getErrorMessage( ) {
        return "Error: " + this.getMessage ( );
    }

    @Override
    public void printStackTrace( ) {
        System.out.println ( this.getErrorMessage ( ) );
    }
}
