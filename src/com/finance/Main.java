package com.finance;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        List<Transaction> transactions = CsvReader.readTransactions("data/transactions.csv");

        Map<String, BigDecimal> budgets = CsvReader.readBudgets("data/categories.csv");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========= Personal Finance Manager =========");
            System.out.println("1. View all transactions");
            System.out.println("2. Search transactions by category");
            System.out.println("3. Search transactions by month");
            System.out.println("4. Generate monthly report");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1 -> transactions.forEach(t ->
                        System.out.println(
                                t.getDate() + " | " +
                                        (t.isIncome() ? "INCOME" : "EXPENSE") + " | " +
                                        t.getCategory() + " | " +
                                        t.getAmount()));

                case 2 -> {
                    System.out.print("Enter category: ");
                    String cat = sc.next();
                    transactions.stream().filter(t -> t.getCategory().equalsIgnoreCase(cat))
                            .forEach(t -> System.out.println(t.getDate() + " | " + t.getCategory() + " | " + t.getAmount()));
                }

                case 3 -> {
                    System.out.print("Enter month (YYYY-MM): ");
                    YearMonth month = YearMonth.parse(sc.next());
                    transactions.stream()
                            .filter(t -> YearMonth.from(t.getDate()).equals(month)) //2024-08-15 -> 2024-08
                            .forEach(t ->
                                    System.out.println(
                                            t.getDate() + " | " +
                                                    t.getCategory() + " | " +
                                                    t.getAmount()));
                }

                case 4 -> {
                    System.out.print("Enter month (YYYY-MM): ");
                    YearMonth month = YearMonth.parse(sc.next());
                    try {
                        Report report = Report.generate(transactions, budgets, month);
                        CsvWriter.write("monthly_summary.csv", List.of(report));
                        System.out.println("✔ Report generated successfully");
                    } catch (OverBudgetException e) {
                        System.out.println("⚠ " + e.getMessage());
                    }
                }

                case 5 -> {
                    System.out.println("Exiting application.");
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
