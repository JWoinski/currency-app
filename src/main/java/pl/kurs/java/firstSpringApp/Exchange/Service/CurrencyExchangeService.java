package pl.kurs.java.firstSpringApp.Exchange.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.Root;
import pl.kurs.java.firstSpringApp.Exchange.Model.Rate;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeService {
    private static final String URL = "http://api.nbp.pl/api/exchangerates/tables/a/?format=json";

    @Autowired
    private final RestTemplate restTemplate;
    private final DBService dbService;
    private  Root root;

    public List<String> getListCodeCurrencies() {
        refreshCurrencyRates();
        List<String> list = Optional.ofNullable(root)
                .map(Root::getRates)
                .stream()
                .flatMap(rates -> rates.stream().map(Rate::getCode))
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            return Collections.emptyList();
        } else {
            list.add("PLN");
            return list;
        }
    }

    double getMidForCurrency(String currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Currency is null");
        }
        return Optional.ofNullable(root)
                .map(Root::getRates)
                .flatMap(rates -> rates.stream()
                        .filter(rate -> rate.getCode().equals(currency))
                        .findFirst()
                        .map(Rate::getMid))
                .orElse(0.0);
    }

    public double exchange(CurrencyExchangeForm exchangeForm) {
        double amount = exchangeForm.getAmount();
        String currencyFrom = exchangeForm.getCurrencyFrom();
        String currencyTo = exchangeForm.getCurrencyTo();
        if (exchangeForm == null || currencyFrom == null || currencyTo == null) {
            throw new IllegalArgumentException("Null values in ExchangeForm");
        }
        refreshCurrencyRates();
        dbService.saveDetailsOfExchangeToDB(exchangeForm);

        if (currencyFrom.equals(currencyTo)) {
            return amount;
        }
        double midFrom = getMidForCurrency(currencyFrom);
        double midTo = getMidForCurrency(currencyTo);

        if (currencyTo.equals("PLN")) {
            return amount * midFrom;
        } else if (currencyFrom.equals("PLN")) {
            return amount / midTo;
        } else {
            return (amount * midFrom) / midTo;
        }
    }

    private void refreshCurrencyRates() {
        ResponseEntity<Root[]> exchange = restTemplate.exchange(URL, HttpMethod.GET, null, Root[].class);
        Optional.ofNullable(exchange.getBody())
                .flatMap(roots -> Arrays.stream(roots).findFirst())
                .ifPresent(root -> this.root = root);
    }
}
