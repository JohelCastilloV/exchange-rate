package com.bcp.exchangerate.service;

import com.bcp.exchangerate.model.CurrencyConversion;
import com.bcp.exchangerate.model.ExchangeRateRequestDto;
import com.bcp.exchangerate.model.ExchangeRateResponseDto;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ExchangeRateService {

    Mono<CurrencyConversion> convertMoney(String currencyFrom, String currencyTo, BigDecimal amount);
    Mono<ExchangeRateResponseDto> saveExchangeRate(ExchangeRateRequestDto requestDto);

}
