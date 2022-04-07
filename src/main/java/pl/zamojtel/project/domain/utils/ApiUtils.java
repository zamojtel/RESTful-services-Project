package pl.zamojtel.project.domain.utils;

import static java.util.Objects.isNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pl.zamojtel.project.domain.models.ApiEnum;


public class ApiUtils {

    public static final String AMDOREN = "https://www.amdoren.com/api/currency.php?api_key=%s&from=%s&to=PLN";
    public static final String OPEN_RATES = "https://openexchangerates.org/api/%s.json?symbols=PLN";
    public static final String NBP = "http://api.nbp.pl/api/exchangerates/rates/a/eur";
    public static final String CURRENCYLAYER = "http://api.currencylayer.com/%saccess_key=%s&currencies=PLN&format=1";

    public static final String LATEST = "latest";
    public static final String PLN = "pln";
    public static final String USDPLN = "USDPLN";
    public static final String RATES = "rates";
    public static final String MID = "mid";
    public static final String AMOUNT = "amount";
    public static final String QUOTES = "quotes";


    private static final String AMDOREN_API = "ziKbFBjzmbj8bMLX6GuVbkjXEkhNeJ";
    private static final String OPENRATES_API = "b96ce505429f4c378807974e676397b7";
    private static final String CURRENCYLAYER_API = "a56795225ca0973fa6838b9a95069aba";

    private ApiUtils() {
    }

    public static JsonObject getResponseFromUrl(String date, String currency, ApiEnum api)
            throws IOException, ParseException {

        String siteUrl = getUrlBasedByApi(date, currency, api);
        if (isNull(siteUrl) || siteUrl.isBlank()) {
            return null;
        }
        URL url = new URL(siteUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (api.equals(ApiEnum.OPEN_RATES)) {
            connection.addRequestProperty("Authorization", "Token " + OPENRATES_API);
        }
        connection.connect();
        if (connection.getResponseCode() == 200) {
            InputStreamReader reader = new InputStreamReader(getInputStreamFromRequest(connection));
            JsonParser parser = new JsonParser();
            return parser.parse(reader).getAsJsonObject();
        }
        return null;
    }

    private static InputStream getInputStreamFromRequest(URLConnection request) throws IOException {
        return (InputStream) request.getContent();
    }

    private static String getUrlBasedByApi(String date, String currency, ApiEnum api) throws ParseException {
        if (isNull(date) || date.isBlank()) {
            date = LATEST;
        }

        if (isNull(currency) || currency.isBlank()) {
            currency = PLN;
        }

        switch (api) {
        case CURRENCYLAYER -> {
            if(date.equals(LATEST)) {
                date = "live?";
            } else {
                date = "historical?date=" + date + "&";
            }
            return String.format(CURRENCYLAYER, date, CURRENCYLAYER_API);
        }
        case NBP -> {
            if(date.equals(LATEST)) {
                return String.format(NBP, currency);
            }
            return NBP + "/" + date;
        }
        case AMDOREN -> {
            return String.format(AMDOREN, AMDOREN_API, currency);
        }
        case OPEN_RATES -> {
            if (!date.equals(LATEST)) {
                date = "/historical/" + date;
            }
            return String.format(OPEN_RATES, date);
        }
        }
        return "";
    }
}
