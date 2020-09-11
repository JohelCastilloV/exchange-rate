package com.bcp.exchangerate;

import com.bcp.exchangerate.model.CurrencyConversion;
import com.bcp.exchangerate.model.ExchangeRateRequestDto;
import com.bcp.exchangerate.model.ExchangeRateResponseDto;
import com.bcp.exchangerate.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

@SpringBootTest
class ExchangeRateApplicationTests {

    @Autowired
    ExchangeRateService exchangeRateService;

    @Test
    void convertMoney(){
        final CurrencyConversion currencyConversionExpected = CurrencyConversion.builder().exchangeRate(new BigDecimal("3.50"))
                .currencyFrom("PEN")
                .currencyTo("USD")
                .totalCalculatedAmount(new BigDecimal("35.00"))
                .amount(BigDecimal.TEN).build();
        StepVerifier.create(exchangeRateService.convertMoney("PEN","USD", BigDecimal.TEN))
                .expectNext(currencyConversionExpected).verifyComplete();

    }

    @Test
    void updateExchangeRate(){
        final ExchangeRateResponseDto exchangeRateResponseDtoExpected = ExchangeRateResponseDto.builder().exchangeRate(new BigDecimal("3.40"))
                .id(10001L).currencyTo("PEN").currencyFrom("USD").build();
        StepVerifier.create(exchangeRateService.saveExchangeRate(ExchangeRateRequestDto.builder().id(10001L)
                .amount(new BigDecimal("3.40")).build()))
                .expectNext(exchangeRateResponseDtoExpected).verifyComplete();

    }}
