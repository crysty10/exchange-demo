package com.luxon.assignment.service.operations;

import com.luxon.assignment.dto.ExchangeRequestDto;
import com.luxon.assignment.enums.ExchangeType;

public interface OperationStrategy {

    void operateExchange(ExchangeRequestDto exchangeRequestDto);

    ExchangeType getExchangeType();
}
