package pl.kurs.java.firstSpringApp.Exchange.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeRatesForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.Rate;
import pl.kurs.java.firstSpringApp.Exchange.Model.Root;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeService {
    private final RestCurrencyApiService restCurrencyApiService;
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
        refreshCurrencyRates();
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
        validateExchangeForm(exchangeForm);

        double amount = exchangeForm.getAmount();
        String currencyFrom = exchangeForm.getCurrencyFrom();
        String currencyTo = exchangeForm.getCurrencyTo();

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
        validateExchangeForm(exchangeForm);

        double amount = exchangeForm.getAmount();
        String currencyFrom = exchangeForm.getCurrencyFrom();
        String currencyTo = exchangeForm.getCurrencyTo();

        if (currencyFrom.equals("PLN")) {
            return amount;
        } else if (currencyTo.equals("PLN")) {
            return amount * getMidForCurrency(currencyFrom);
        } else {
            return amount * getMidForCurrency(currencyFrom);
        }
    }

    private void validateExchangeForm(CurrencyExchangeForm exchangeForm) {
        if (exchangeForm == null || exchangeForm.getCurrencyFrom() == null || exchangeForm.getCurrencyTo() == null) {
            throw new IllegalArgumentException("Invalid exchange form");
        }
    }

    private void refreshCurrencyRates() {
        if(root == null){
            root = restCurrencyApiService.getApiResponse();
        }
    }

    public List<Rate> getExchangeRatesForDatePeriod(CurrencyExchangeRatesForm exchangeRatesForm) {
        LocalDate startDate = LocalDate.parse(exchangeRatesForm.getStartDate());
        LocalDate endDate = LocalDate.parse(exchangeRatesForm.getEndDate());
        if(startDate.isAfter(LocalDate.now()) ||
                endDate.isAfter(LocalDate.now()) ||
                startDate.isAfter(endDate) ||
                startDate.isEqual(endDate)){
            return List.of();
        }
        return restCurrencyApiService.getApiResponse(exchangeRatesForm.getCurrency(), exchangeRatesForm.getStartDate(), exchangeRatesForm.getEndDate()).getRates();
    }

    public String getChartData(CurrencyExchangeRatesForm exchangeRatesForm) {
        List<Rate> exchangeRatesForDatePeriod = getExchangeRatesForDatePeriod(exchangeRatesForm);

        if (exchangeRatesForDatePeriod == null || exchangeRatesForDatePeriod.isEmpty()) {
            return "";
        }

        String dates = exchangeRatesForDatePeriod.stream()
                .map(rate -> "\"" + rate.getEffectiveDate() + "\"")
                .collect(Collectors.joining(","));

        String rates = exchangeRatesForDatePeriod.stream()
                .map(rate -> String.valueOf(rate.getMid()))
                .collect(Collectors.joining(","));

        return String.format("{ \"labels\": [%s], \"data\": [%s] }", dates, rates);
    }
}