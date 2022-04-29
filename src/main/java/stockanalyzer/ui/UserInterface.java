package stockanalyzer.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import stockanalyzer.ctrl.Controller;

public class UserInterface {

    Controller controller = new Controller ( );

    public void getDataFromCtrl1 ( ) {
        controller.process ( "AAPL" );
    }


    public void getDataFromCtrl2 ( ) {
        controller.process ( "TSLA" );

    }

    public void getDataFromCtrl3 ( ) {
        controller.process ( "GOOG" );

    }

    public void getDataFromCtrl4 ( ) {

    }

    public void getDataForCustomInput ( ) {

    }

    public void start ( ) {
        Menu<Runnable> menu = new Menu<> ( "User Interface" );
        menu.setTitel ( "Wählen Sie aus:" );
        menu.insert ( "a" , "Apple Inc " , this::getDataFromCtrl1 );
        menu.insert ( "b" , "Tesla" , this::getDataFromCtrl2 );
        menu.insert ( "c" , "Google" , this::getDataFromCtrl3 );
        menu.insert ( "d" , "Choice User Imput:" , this::getDataForCustomInput );
        menu.insert ( "z" , "Choice User Imput:" , this::getDataFromCtrl4 );
        menu.insert ( "q" , "Quit" , null );
        Runnable choice;
        while ( ( choice = menu.exec ( ) ) != null ) {
            choice.run ( );
        }
        controller.closeConnection ( );
        System.out.println ( "Program finished" );
    }


    protected String readLine ( ) throws IOException, NullPointerException {
        String value = "\0";
        BufferedReader inReader = new BufferedReader ( new InputStreamReader ( System.in ) );
        try {
            value = inReader.readLine ( );
        } catch ( IOException | NullPointerException e ) {
            System.out.println ( "We are sorry this happened." );
        }
        return value.trim ( );
    }

    protected Double readDouble ( int lowerlimit , int upperlimit ) throws IOException {
        Double number = null;
        while ( number == null ) {
            String str = this.readLine ( );
            try {
                number = Double.parseDouble ( str );
            } catch ( NumberFormatException e ) {
                number = null;
                System.out.println ( "Please enter a valid number:" );
                continue;
            }
            if (number < lowerlimit) {
                System.out.println ( "Please enter a higher number:" );
                number = null;
            } else if (number > upperlimit) {
                System.out.println ( "Please enter a lower number:" );
                number = null;
            }
        }
        return number;
    }
}
