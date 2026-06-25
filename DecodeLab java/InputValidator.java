public class InputValidator {

    private static final String[] KNOWN_CURRENCIES = {
        "USD", "EUR", "GBP", "PKR", "AED", "SAR", "INR",
        "JPY", "CAD", "AUD", "CHF", "CNY", "NZD", "SGD",
        "HKD", "NOK", "SEK", "DKK", "MXN", "BRL", "ZAR",
        "TRY", "KWD", "QAR", "BHD", "OMR", "EGP", "MYR"
    };

    public static boolean isValidFormat(String currencyCode) {
        if (currencyCode == null || currencyCode.isEmpty()) {
            return false;
        }
        return currencyCode.matches("[A-Z]{3}");
    }

    public static boolean isKnownCurrency(String currencyCode) {
        for (String known : KNOWN_CURRENCIES) {
            if (known.equals(currencyCode)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0 && amount <= 1_000_000_000;
    }

    public static boolean areDifferentCurrencies(String source, String target) {
        return !source.equals(target);
    }
}