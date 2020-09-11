package com.bcp.exchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversion {

    private BigDecimal amount;
    private BigDecimal totalCalculatedAmount;
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal exchangeRate;


}
