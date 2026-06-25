import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ApiService {

    private static final String API_KEY = "YOUR_API_KEY_HERE";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    private static String buildUrl(String baseCurrency) {
        return BASE_URL + API_KEY + "/latest/" + baseCurrency;
    }

    public static String fetchExchangeRates(String baseCurrency) throws IOException {
        String urlString = buildUrl(baseCurrency);
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        int responseCode = connection.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("API request failed. HTTP code: " + responseCode);
        }

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream()));

        StringBuilder responseBody = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            responseBody.append(line);
        }

        reader.close();
        connection.disconnect();

        return responseBody.toString();
    }
}