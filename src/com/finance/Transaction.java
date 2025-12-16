package com.finance;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Transaction {

    protected LocalDate date;
    protected BigDecimal amount;
    protected String category;
    protected String currency;
    protected String description;

    protected Transaction(LocalDate date, BigDecimal amount, String category, String currency, String description) {

        if (!currency.equals("USD"))
            throw new InvalidCurrencyException(currency);

        this.date = date;
        this.amount = amount;
        this.category = category;
        this.currency = currency;
        this.description = description;
    }

    public abstract boolean isIncome();

    public BigDecimal getAmount() {
        return amount;
    }
    public String getCategory() {

        return category;
    }
    public LocalDate getDate() {
        return date;
    }
}

