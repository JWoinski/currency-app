package pl.kurs.java.firstSpringApp.Exchange.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.Rate;
import pl.kurs.java.firstSpringApp.Exchange.Model.Root;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeService {
    private final RestCurrencyApiService restCurrencyApiService;
//    private final DBService dbService;
    private Root root;

    public List<String> getListCodeCurrencies() {
        refreshCurrencyRates();
        List<String> list = Optional.ofNullable(root)
                .map(Root::getRates)
                .stream()
                .flatMap(rates -> rates.stream().map(Rate::getCode))
                .collect(Collectors.toList());
        list.add("PLN");
        return list;
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
        double valueExchangeInPLN = getValueExchangeInPLN(exchangeForm);
        if (exchangeForm == null || currencyFrom == null || currencyTo == null) {
            throw new IllegalArgumentException("Null values in ExchangeForm");
        }
        refreshCurrencyRates();
        //TODO SPY

        //deploy to heroku, do not need it for expose on website
//        dbService.saveDetailsOfExchangeToDB(exchangeForm, valueExchangeInPLN);

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

    public double getValueExchangeInPLN(CurrencyExchangeForm exchangeForm) {
        if (exchangeForm.getCurrencyTo() == null || exchangeForm.getCurrencyFrom() == null) {
            throw new IllegalArgumentException("One of currency is null");
        }
        if (exchangeForm.getCurrencyFrom().equals("PLN")) {
            return exchangeForm.getAmount();
        } else if (exchangeForm.getCurrencyTo().equals("PLN")) {
            return exchangeForm.getAmount() * getMidForCurrency(exchangeForm.getCurrencyFrom());
        } else {
            return (exchangeForm.getAmount() * getMidForCurrency(exchangeForm.getCurrencyFrom()));
        }
    }

    private void refreshCurrencyRates() {
        root = restCurrencyApiService.getApiResponse();
    }
}
