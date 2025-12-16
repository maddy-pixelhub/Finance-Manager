package com.finance;
import java.math.BigDecimal;

public class FlatTaxStrategy implements TaxStrategy {
    public BigDecimal calculateTax(BigDecimal income) {
        return income.multiply(new BigDecimal("0.10"));
    }
}

