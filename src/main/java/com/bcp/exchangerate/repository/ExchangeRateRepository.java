package com.bcp.exchangerate.repository;

import com.bcp.exchangerate.model.ExchangeRateEntity;
import org.springframework.data.repository.CrudRepository;

public interface ExchangeRateRepository extends CrudRepository<ExchangeRateEntity,Long> {

    ExchangeRateEntity findByFromAndTo(String from, String to);

}
