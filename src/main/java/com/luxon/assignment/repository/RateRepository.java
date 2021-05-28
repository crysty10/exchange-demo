package com.luxon.assignment.repository;

import com.luxon.assignment.entity.Account;
import com.luxon.assignment.entity.Rate;
import com.luxon.assignment.enums.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate,Integer> {

    Rate findRateByBaseInstrumentAndQuoteInstrument(Instrument baseInstrument, Instrument quoteInstrument);
}
