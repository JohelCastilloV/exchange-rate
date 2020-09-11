package com.bcp.exchangerate.web;


import com.bcp.exchangerate.model.CurrencyConversion;
import com.bcp.exchangerate.model.ExchangeRateRequestDto;
import com.bcp.exchangerate.model.ExchangeRateResponseDto;
import com.bcp.exchangerate.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {


    private final ExchangeRateService exchangeRateService;

    @Operation(summary = "Convert money from one currency to another",security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping
    public Mono<ResponseEntity<CurrencyConversion>> calculateExchangeRate(String currencyFrom, String currencyTo, BigDecimal amount) {
        return exchangeRateService.convertMoney(currencyFrom, currencyTo, amount)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Update exchange rate")
    @PostMapping
    public Mono<ResponseEntity<ExchangeRateResponseDto>> updateExchangeRate(@RequestBody ExchangeRateRequestDto requestDto) {
        return exchangeRateService.saveExchangeRate(requestDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
