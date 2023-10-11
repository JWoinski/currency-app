package pl.kurs.java.firstSpringApp.exchange.operationChart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.java.firstSpringApp.exchange.model.GraphResult;
import pl.kurs.java.firstSpringApp.exchange.service.dataBaseService.CurrencyFormRepository;
import pl.kurs.java.firstSpringApp.exchange.service.dataBaseService.DBService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyExchangePerCurrency implements ExchangeChart {
    private final DBService dbService; // PAMIETAC O REQUIREDARGSCONSTRUCTOR
    private final CurrencyFormRepository currencyFormRepository;

    @Override
    public String getName() {
        return "Number of exchanges per currency";
    }

    @Override
    public GraphResult execute() {
        return new GraphResult(getName(), "Values", "Number of exchanges", getValuesToSurveyMap());
    }

    public Map<String, Integer> getValuesToSurveyMap() {
//        return dbService.getCurrencyExchangePerCurrency();
        return currencyFormRepository.getCurrencyExchangePerCurrency();
    }
}