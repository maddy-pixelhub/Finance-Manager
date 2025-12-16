package com.finance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    // Read transactions CSV
    public static List<Transaction> readTransactions(String filePath) {

        List<Transaction> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) continue;

                String[] t = line.split(",");
                if (t.length < 6) continue;

                LocalDate date = LocalDate.parse(t[0].trim());
                String type = t[1].trim();
                BigDecimal amount = new BigDecimal(t[2].trim());
                String category = t[3].trim();
                String currency = t[4].trim();
                String description = t[5].trim();

                Transaction tx = type.equalsIgnoreCase("INCOME")
                        ? new Income(date, amount, category, currency, description)
                        : new Expense(date, amount, category, currency, description);

                list.add(tx);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Read budgets CSV
    public static Map<String, BigDecimal> readBudgets(String filePath) {

        Map<String, BigDecimal> budgets = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) continue;

                String[] t = line.split(",");
                if (t.length < 2) continue;

                String category = t[0].trim();
                BigDecimal budget = new BigDecimal(t[1].trim());

                budgets.put(category, budget);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return budgets;
    }
}
