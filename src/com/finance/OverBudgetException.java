package com.finance;
import java.math.BigDecimal;

public class OverBudgetException extends RuntimeException {
    public OverBudgetException(String c, BigDecimal s, BigDecimal l) {
        super("Over budget: " + c + " spent=" + s + " limit=" + l);
    }
}
