package com.luxon.assignment.service.operations;

import com.luxon.assignment.dto.ExchangeRequestDto;
import com.luxon.assignment.enums.ExchangeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendOperation implements OperationStrategy {
    @Override
    public void operateExchange(ExchangeRequestDto exchangeRequestDto) {

    }

    @Override
    public ExchangeType getExchangeType() {
        return ExchangeType.SEND;
    }
}
