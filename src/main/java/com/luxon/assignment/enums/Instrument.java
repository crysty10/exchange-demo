package com.luxon.assignment.enums;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;

public enum Instrument {
    BTC, ETH, BCH, LTC, USD, EUR, GBP;

    private static final List<Instrument> crypto = Arrays.asList(BTC, ETH, BCH, LTC);
    private static final List<Instrument> currency = Arrays.asList(USD, EUR, GBP);

    public Boolean isCryptoInstrument() {
        return crypto.contains(this);
    }

    public Boolean isFiatInstrument() {
        return !isCryptoInstrument();
    }

    @SneakyThrows
    public Boolean isCurrencyPair(Instrument base, Instrument quote) {
        return crypto.contains(base) && currency.contains(quote);
    }
}
