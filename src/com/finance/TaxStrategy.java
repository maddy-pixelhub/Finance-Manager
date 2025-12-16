package com.finance;
import java.math.BigDecimal;

public interface TaxStrategy {
    BigDecimal calculateTax(BigDecimal income);
}

