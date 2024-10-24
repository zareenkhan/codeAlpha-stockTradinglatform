import java.util.*;

public class StockTradingPlatform {

    // Simulated stock market data
    private static final Map<String, Double> stockPrices = new HashMap<>();
    private static final Map<String, Integer> portfolio = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static double cashBalance = 10000.00; // Starting cash balance

    public static void main(String[] args) {
        // Initialize market data
        initializeMarketData();

        // Main loop for user interaction
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> displayMarketData();
                case 2 -> buyStock();
                case 3 -> sellStock();
                case 4 -> displayPortfolio();
                case 5 -> {
                    System.out.println("Exiting the platform. Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Initialize stock market data with some simulated stock prices
    private static void initializeMarketData() {
        stockPrices.put("AAPL", 150.00);
        stockPrices.put("GOOGL", 2800.00);
        stockPrices.put("AMZN", 3400.00);
        stockPrices.put("TSLA", 700.00);
        stockPrices.put("MSFT", 300.00);
    }

    // Display the main menu options1
    private static void displayMenu() {
        System.out.println("\n--- Stock Trading Platform ---");
        System.out.println("1. View Market Data");
        System.out.println("2. Buy Stocks");
        System.out.println("3. Sell Stocks");
        System.out.println("4. View Portfolio");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    // Display current stock prices
    private static void displayMarketData() {
        System.out.println("\n--- Market Data ---");
        for (Map.Entry<String, Double> entry : stockPrices.entrySet()) {
            System.out.println(entry.getKey() + ": $" + entry.getValue());
        }
    }

    // Handle buying stocks
    private static void buyStock() {
        System.out.println("AAPl, GOOGL, AMZN, TSLA, MSFT");
        System.out.print("Enter the stock symbol to buy: ");
        String symbol = scanner.nextLine().toUpperCase();

        if (!stockPrices.containsKey(symbol)) {
            System.out.println("Stock symbol not found. Please check the symbol and try again.");
            return;
        }

        System.out.print("Enter the quantity to buy: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        double price = stockPrices.get(symbol);
        double totalCost = price * quantity;

        if (totalCost > cashBalance) {
            System.out.println("Insufficient funds. You have $" + cashBalance + " available, but the cost is $" + totalCost + ".");
            return;
        }

        cashBalance -= totalCost;
        portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
        System.out.printf("Successfully purchased %d shares of %s at $%.2f each. Total cost: $%.2f.%n", quantity, symbol, price, totalCost);
    }

    // Handle selling stocks
    private static void sellStock() {
        System.out.print("Enter the stock symbol to sell: ");
        String symbol = scanner.nextLine().toUpperCase();

        if (!portfolio.containsKey(symbol) || portfolio.get(symbol) == 0) {
            System.out.println("You don't own this stock or your portfolio does not include this stock.");
            return;
        }

        System.out.print("Enter the quantity to sell: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        int ownedQuantity = portfolio.get(symbol);
        if (quantity > ownedQuantity) {
            System.out.println("You don't have enough shares to sell. You own only " + ownedQuantity + " shares.");
            return;
        }

        double price = stockPrices.get(symbol);
        double totalRevenue = price * quantity;

        cashBalance += totalRevenue;
        portfolio.put(symbol, ownedQuantity - quantity);
        System.out.printf("Successfully sold %d shares of %s at $%.2f each. Total revenue: $%.2f.%n", quantity, symbol, price, totalRevenue);
    }

    // Display the current portfolio
    private static void displayPortfolio() {
        System.out.println("\n--- Portfolio ---");
        System.out.printf("Cash Balance: $%.2f%n", cashBalance);

        if (portfolio.isEmpty()) {
            System.out.println("You don't own any stocks.");
            return;
        }

        double totalValue = cashBalance;
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            String symbol = entry.getKey();
            int quantity = entry.getValue();
            double price = stockPrices.get(symbol);
            double value = price * quantity;
            totalValue += value;
            System.out.printf("%s: %d shares, Current Value: $%.2f%n", symbol, quantity, value);
        }

        System.out.printf("Total Portfolio Value: $%.2f%n", totalValue);
    }
}
