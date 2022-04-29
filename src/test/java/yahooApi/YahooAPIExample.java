package yahooApi;

import stockanalyzer.ctrl.ExceptionController;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;

import java.util.Arrays;
import java.util.List;

public class YahooAPIExample {

    public static void main(String[] args) throws ExceptionController {

        YahooFinance yahooFinance = new YahooFinance();
        List<String> tickers = Arrays.asList("AMZN", "TSLA", "GOOG");
        YahooResponse response = yahooFinance.getCurrentData(tickers);
        QuoteResponse quotes = response.getQuoteResponse();
        quotes.getResult().forEach( quote -> System.out.println(quote.getAsk()));

        System.out.println("***ATX***");
        YahooResponse response2 = yahooFinance.getCurrentData(Arrays.asList("OMV.VI",
                "EBS.VI","DOC.VI","SBO.VI","RBI.VI","VIG.VI","TKA.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI",
                "WIE.VI","CAI.VI","BG.VI","POST.VI","LNZ.VI","UQA.VI","SPI.VI","ATS.VI","IIA.VI"));
        QuoteResponse quotes2 = response2.getQuoteResponse();
        quotes2.getResult().stream().forEach(quote -> System.out.println(quote.getShortName()+";"+quote.getAsk()+";"+quote.getBid()+" "+quote.getRegularMarketPreviousClose()));


    }
}
