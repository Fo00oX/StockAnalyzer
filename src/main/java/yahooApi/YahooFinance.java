package yahooApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import stockanalyzer.ctrl.ExceptionController;
import yahooApi.beans.Asset;
import yahooApi.beans.YahooResponse;

import javax.json.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YahooFinance {

    public static final String URL_YAHOO = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=%s";

    public static String requestData( List<String> tickers ) throws NullPointerException, ExceptionController {

        String symbols = String.join(",", tickers);
        String query = String.format(URL_YAHOO, symbols);
        System.out.println(query);
        URL obj = null;
        try {
            obj = new URL(query);
        } catch (MalformedURLException | NullPointerException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        StringBuilder response = new StringBuilder();
        try {
            assert obj != null;
            con = (HttpURLConnection) obj.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    protected JsonObject convert(String jsonResponse) throws NullPointerException, JsonProcessingException {
        InputStream is = new ByteArrayInputStream(jsonResponse.getBytes( ));
        JsonReader reader = Json.createReader(is);
        JsonObject jo = reader.readObject();
        reader.close();
        return jo;
    }

    public void fetchAssetName(Asset asset) throws ExceptionController, JsonProcessingException {
        YahooFinance yahoo = new YahooFinance();
        List<String> symbols = new ArrayList<>();
        symbols.add(asset.getSymbol());
        String jsonResponse = null;
        jsonResponse = requestData(symbols);
        JsonObject jo = yahoo.convert(jsonResponse);
        asset.setName(extractName(jo));
    }

    private String extractName(JsonObject jo) {
        String returnName = "";
        Map stockData = ((Map) jo.getJsonObject("quoteResponse"));
        JsonArray x = (JsonArray) stockData.get("result");
        JsonObject y = (JsonObject) x.get(0);
        JsonValue name = y.get("longName");
        returnName = name.toString();
        return returnName;
    }

    public static YahooResponse getCurrentData( List<String> tickers ) throws ExceptionController {
        String jsonResponse = requestData(tickers);
        ObjectMapper objectMapper = new ObjectMapper();
        YahooResponse result = null;
        try {
             result  = objectMapper.readValue(jsonResponse, YahooResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}