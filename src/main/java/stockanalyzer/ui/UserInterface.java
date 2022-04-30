package stockanalyzer.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import stockanalyzer.ctrl.Controller;

public class UserInterface {

    public static void printError(String errorMessage){
        System.out.println(errorMessage);
    }

    private Controller ctrl = new Controller();

    public void getDataFromCtrl1() {
            ctrl.process ( "AAPL,TSLA,GOOG" );
    }

    public void getDataFromCtrl2() {
        ctrl.process("TSLA");
    }

    public void getDataFromCtrl3() {
        ctrl.process("GOOG");
    }


    public void start() {
        Menu<Runnable> menu = new Menu<>("User Interface");
        menu.setTitel("Wählen Sie aus:");
        menu.insert("a", "AAPL,TSLA,GOOG", this::getDataFromCtrl1);
        menu.insert("b", "BABA", this::getDataFromCtrl2);
        menu.insert("c", "GOOG", this::getDataFromCtrl3);
        menu.insert("q", "Quit", null);
        Runnable choice;
        while ((choice = menu.exec()) != null) {
            choice.run();
        }
        ctrl.closeConnection();
        System.out.println("Program finished");
    }


    protected String readLine()
    {
        String value = "\0";
        BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            value = inReader.readLine();
        } catch (IOException ignored ) {
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
                number=null;
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