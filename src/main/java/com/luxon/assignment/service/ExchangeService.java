package com.luxon.assignment.service;

import com.luxon.assignment.dto.ExchangeRequestDto;
import com.luxon.assignment.entity.Account;
import com.luxon.assignment.entity.Balance;
import com.luxon.assignment.entity.Rate;
import com.luxon.assignment.entity.Wallet;
import com.luxon.assignment.enums.ExchangeType;
import com.luxon.assignment.enums.Instrument;
import com.luxon.assignment.repository.AccountRepository;
import com.luxon.assignment.repository.BalanceRepository;
import com.luxon.assignment.repository.RateRepository;
import com.luxon.assignment.repository.WalletRepository;
import com.luxon.assignment.service.operations.OperationStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class ExchangeService {

    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;
    private final RateRepository rateRepository;
    private final WalletRepository walletRepository;
    private final Map<ExchangeType, OperationStrategy> operationStrategyMapping;

    public ExchangeService(AccountRepository accountRepository, BalanceRepository balanceRepository,
                           RateRepository rateRepository, WalletRepository walletRepository,
                           List<OperationStrategy> operationStrategies) {
        this.accountRepository = accountRepository;
        this.balanceRepository = balanceRepository;
        this.rateRepository = rateRepository;
        this.walletRepository = walletRepository;
        this.operationStrategyMapping = operationStrategies.stream()
                .collect(toMap(OperationStrategy::getExchangeType, identity()));
    }

    public ResponseEntity<?> execute(ExchangeRequestDto exchangeRequestDto) {
        OperationStrategy operationStrategy = Optional.ofNullable(operationStrategyMapping.get(exchangeRequestDto.getExchangeType()))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Exchange type %s not supported", exchangeRequestDto.getExchangeType())));
        operationStrategy.operateExchange(exchangeRequestDto);

        return ResponseEntity.ok(200);
    }

    @PostConstruct
    public void doSome() {
        rateRepository.save(Rate.builder().id(1)
                .baseInstrument(Instrument.BTC)
                .quoteInstrument(Instrument.USD)
                .value(10d).build());

        Account jackBlackAccount = accountRepository.save(Account.builder().id(1).name("Jack Black").build());

        jackBlackAccount.setBalances(Collections.singletonList(
                balanceRepository.save(Balance.builder().id(1).instrument(Instrument.BTC).qty(10d).account(jackBlackAccount).build())));
        jackBlackAccount.setBalances(Collections.singletonList(
                balanceRepository.save(Balance.builder().id(2).instrument(Instrument.USD).qty(1000d).account(jackBlackAccount).build())));
        jackBlackAccount.setWallets(Collections.singletonList(
                walletRepository.save(Wallet.builder().id(1).instrument(Instrument.BTC).walletAddress("Jack Black Address").account(jackBlackAccount).build())));

        Account jonDoeAccount = accountRepository.save(Account.builder().id(2).name("Jon Doe").build());
        jonDoeAccount.setBalances(Collections.singletonList(
                balanceRepository.save(Balance.builder().id(3).instrument(Instrument.BTC).qty(8d).account(jonDoeAccount).build())));
        jonDoeAccount.setWallets(Collections.singletonList(
                walletRepository.save(Wallet.builder().id(2).instrument(Instrument.BTC).walletAddress("Jon Doe Address").account(jonDoeAccount).build())));
    }
}
