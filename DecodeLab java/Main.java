import java.util.Scanner;
import java.io.IOException;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Currency Converter");

        String sourceCurrency = getCurrencyInput(scanner,
            "Enter source currency (e.g. USD): ");

        String targetCurrency = getCurrencyInput(scanner,
            "Enter target currency (e.g. PKR): ");

        while (!InputValidator.areDifferentCurrencies(sourceCurrency, targetCurrency)) {
            System.out.println("  ⚠ Source and target currencies must be different.");
            targetCurrency = getCurrencyInput(scanner, "Enter target currency again: ");
        }

        double amount = getAmountInput(scanner);
        scanner.close();

        System.out.println("\n Converting " + amount + " " +
                           sourceCurrency + " to " + targetCurrency + "...\n");

        try {
            double convertedAmount = CurrencyConverter.convert(
                sourceCurrency, targetCurrency, amount);

            System.out.printf("✓ Result: %.2f %s = %.2f %s%n",
                amount, sourceCurrency, convertedAmount, targetCurrency);

        } catch (UnknownHostException e) {
            System.out.println("✗ No internet connection. Please check your network.");

        } catch (IllegalArgumentException e) {
            System.out.println("✗ Invalid currency: " + e.getMessage());

        } catch (IOException e) {
            System.out.println("✗ API error: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
        }
    }

    private static String getCurrencyInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();

            if (!InputValidator.isValidFormat(input)) {
                System.out.println("  ⚠ Must be exactly 3 letters (e.g. USD).");
            } else if (!InputValidator.isKnownCurrency(input)) {
                System.out.println("  ⚠ Warning: '" + input +
                    "' not in known list, but we'll try it.");
                return input;
            } else {
                return input;
            }
        }
    }

    private static double getAmountInput(Scanner scanner) {
        while (true) {
            System.out.print("Enter amount to convert: ");
            String input = scanner.nextLine().trim();
            try {
                double amount = Double.parseDouble(input);
                if (!InputValidator.isValidAmount(amount)) {
                    System.out.println("  ⚠ Amount must be between 0 and 1 billion.");
                } else {
                    return amount;
                }
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Please enter a valid number like 100.50");
            }
        }
    }
}