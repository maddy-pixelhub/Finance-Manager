package com.finance;

import java.math.*;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class Report implements Reportable {

    private YearMonth month;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal savingsRatio;

    private Report() {}

    public static class Builder {
        private final Report r = new Report();
        public Builder month(YearMonth m) {
            r.month = m;
            return this;
        }
        public Builder income(BigDecimal i) { r.income = i;
            return this;
        }
        public Builder expense(BigDecimal e) { r.expense = e;
            return this;
        }
        public Builder savingsRatio(BigDecimal s) { r.savingsRatio = s;
            return this;
        }
        public Report build() { return r; }
    }

    public static Report generate(List<Transaction> txs,
                                  Map<String, BigDecimal> budgets,
                                  YearMonth month) {

        List<Transaction> monthly = txs.stream()
                .filter(t -> YearMonth.from(t.getDate()).equals(month))
                .toList();

        BigDecimal income = monthly.stream()
                .filter(Transaction::isIncome)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = monthly.stream()
                .filter(t -> !t.isIncome())
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, List<Transaction>> byCategory =
                monthly.stream().filter(t -> !t.isIncome())
                        .collect(Collectors.groupingBy(Transaction::getCategory));

        byCategory.forEach((cat, list) -> {
            BigDecimal spent = list.stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (budgets.containsKey(cat) &&
                    spent.compareTo(budgets.get(cat)) > 0)
                throw new OverBudgetException(cat, spent, budgets.get(cat));
        });

        BigDecimal ratio = income.signum() == 0 ? BigDecimal.ZERO :
                income.subtract(expense)
                        .divide(income, 2, RoundingMode.HALF_UP);

        return new Builder()
                .month(month)
                .income(income)
                .expense(expense)
                .savingsRatio(ratio)
                .build();
    }

    public String toCsv() {
        return month + "," + income + "," + expense + "," + savingsRatio;
    }
}

