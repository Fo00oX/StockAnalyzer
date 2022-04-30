import stockanalyzer.ctrl.YahooException;
import stockanalyzer.ui.UserInterface;

public class MCP {

	public static void main(String args[])
	{
		try {

			UserInterface ui = new UserInterface();
			ui.start();

		}catch ( Exception e ) {
			UserInterface.print ( "An Error acquired");
		}
	}
}
