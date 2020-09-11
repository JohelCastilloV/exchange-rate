package com.bcp.exchangerate.service;


import com.bcp.exchangerate.model.CurrencyConversion;
import com.bcp.exchangerate.model.ExchangeRateRequestDto;
import com.bcp.exchangerate.model.ExchangeRateResponseDto;
import com.bcp.exchangerate.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;


    @Override
    public Mono<CurrencyConversion> convertMoney(String currencyFrom, String currencyTo, BigDecimal amount) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> exchangeRateRepository.findByFromAndTo(currencyFrom, currencyTo)))
                .doOnSubscribe(subscription -> log.info("convertMoney() START currencyFrom: {} currencyTo: {} amount: {}", currencyFrom, currencyTo, amount))
                .map(exchangeRateEntity -> CurrencyConversion.builder()
                        .amount(amount)
                        .currencyFrom(currencyFrom)
                        .currencyTo(currencyTo)
                        .exchangeRate(exchangeRateEntity.getExchangeRate())
                        .totalCalculatedAmount(amount.multiply(exchangeRateEntity.getExchangeRate()))
                        .build())
                .doOnError(throwable -> log.error("convertMoney() ERROR", throwable))
                .doFinally(signalType -> log.info("convertMoney() END"));
    }

    @Override
    public Mono<ExchangeRateResponseDto> saveExchangeRate(ExchangeRateRequestDto requestDto) {
        return Mono.defer(() -> Mono.just(exchangeRateRepository.findById(requestDto.getId())))
                .doOnSubscribe(subscription -> log.info("saveExchangeRate() requestDto: {}", requestDto))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(exchangeRateEntity -> {
                            exchangeRateEntity.setExchangeRate(requestDto.getAmount());
                            return exchangeRateRepository.save(exchangeRateEntity);
                        }
                )
                .map(exchangeRateEntity -> ExchangeRateResponseDto.builder()
                        .id(exchangeRateEntity.getId())
                        .exchangeRate(exchangeRateEntity.getExchangeRate())
                        .currencyFrom(exchangeRateEntity.getFrom())
                        .currencyTo(exchangeRateEntity.getTo())
                        .build())
                .doOnError(throwable -> log.error("saveExchangeRate() ERROR", throwable))
                .doFinally(signalType -> log.info("saveExchangeRate() END"));
    }
}
