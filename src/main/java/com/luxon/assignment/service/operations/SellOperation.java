package com.luxon.assignment.service.operations;

import com.luxon.assignment.dto.ExchangeRequestDto;
import com.luxon.assignment.entity.Account;
import com.luxon.assignment.entity.Balance;
import com.luxon.assignment.entity.Rate;
import com.luxon.assignment.enums.ExchangeType;
import com.luxon.assignment.repository.AccountRepository;
import com.luxon.assignment.repository.BalanceRepository;
import com.luxon.assignment.repository.RateRepository;
import com.luxon.assignment.service.exceptions.InsufficientBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellOperation implements OperationStrategy {

    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;
    private final RateRepository rateRepository;

    @Override
    public void operateExchange(ExchangeRequestDto exchangeRequestDto) {
        Account account = accountRepository.getOne(exchangeRequestDto.getAccountId());

        // BTN to USD
        Balance baseBalance = account.getBalanceByInstrument(exchangeRequestDto.getBaseInstrument());
        if (baseBalance.getQty().compareTo(exchangeRequestDto.getQuantityToTrade()) < 0) {
            throw new InsufficientBalance(String.format("Not enough balance to sell %s of %s.",
                    exchangeRequestDto.getQuantityToTrade(), exchangeRequestDto.getBaseInstrument()));
        } else {
            Balance quoteBalance = account.getBalanceByInstrument(exchangeRequestDto.getQuoteInstrument());
            Rate rate = rateRepository.findRateByBaseInstrumentAndQuoteInstrument(
                    exchangeRequestDto.getBaseInstrument(), exchangeRequestDto.getQuoteInstrument());
            synchronized (this) {
                baseBalance.setQty(baseBalance.getQty() - exchangeRequestDto.getQuantityToTrade());
                balanceRepository.save(baseBalance);

                Double ratePerQuantity = rate.getValue() * exchangeRequestDto.getQuantityToTrade();
                quoteBalance.setQty(quoteBalance.getQty() + ratePerQuantity);
                balanceRepository.save(quoteBalance);
            }
        }

    }

    @Override
    public ExchangeType getExchangeType() {
        return ExchangeType.SELL;
    }
}
