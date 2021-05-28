package com.luxon.assignment.entity;

import com.luxon.assignment.enums.Instrument;
import com.luxon.assignment.service.exceptions.NoBalance;
import com.luxon.assignment.service.exceptions.NoWallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Account {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "account")
    private List<Balance> balances;

    @OneToMany(mappedBy = "account")
    private List<Wallet> wallets;

    public Balance getBalanceByInstrument(Instrument instrument) {
        return balances.stream()
                .filter(balance -> balance.getInstrument() == instrument)
                .filter(balance -> balance.getQty().compareTo(0D) != 0)
                .findFirst()
                .orElseThrow(() -> new NoBalance(String.format("No balance found for %s instrument", instrument)));
    }

    public Wallet getWalletForInstrument(Instrument instrument) {
        return wallets.stream()
                .filter(wallet -> wallet.getInstrument() == instrument)
                .findFirst()
                .orElseThrow(() -> new NoWallet(String.format("No wallet found for %s instrument", instrument)));
    }
}
