package com.luxon.assignment.dto;

import com.luxon.assignment.enums.ExchangeType;
import com.luxon.assignment.enums.Instrument;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ExchangeRequestDto {

    @NotNull
    private Integer accountId;

    @NotNull
    private ExchangeType exchangeType;

    @NotNull
    // todo add validation that is crypto
    private Instrument baseInstrument;

    @NotNull
    // todo add validation that is currency
    private Instrument quoteInstrument;

    @NotNull
    private Double quantityToTrade;
}
