package com.finance;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense extends Transaction {
    public Expense(LocalDate d, BigDecimal a, String c, String cur, String desc) {
        super(d, a, c, cur, desc);
    }
    public boolean isIncome() { return false; }
}

