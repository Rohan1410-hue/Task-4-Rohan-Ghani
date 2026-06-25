import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

public class CurrencyConverter {

    public static double convert(String sourceCurrency,
                                  String targetCurrency,
                                  double amount) throws IOException {

        String jsonResponse = ApiService.fetchExchangeRates(sourceCurrency);

        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        String result = jsonObject.get("result").getAsString();

        if (!result.equals("success")) {
            throw new IOException("API error: " +
                jsonObject.get("error-type").getAsString());
        }

        JsonObject conversionRates = jsonObject
            .get("conversion_rates")
            .getAsJsonObject();

        if (!conversionRates.has(targetCurrency)) {
            throw new IllegalArgumentException(
                "Currency not found: " + targetCurrency);
        }

        double exchangeRate = conversionRates.get(targetCurrency).getAsDouble();

        return amount * exchangeRate;
    }
}