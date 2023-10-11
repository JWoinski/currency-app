package pl.kurs.java.firstSpringApp.exchange.operationChart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.java.firstSpringApp.exchange.model.GraphResult;
import pl.kurs.java.firstSpringApp.exchange.service.dataBaseService.CurrencyFormRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyExchangePerValue implements ExchangeChart {
    //    private final DBService dbService;
    private final CurrencyFormRepository currencyFormRepository;

    @Override
    public String getName() {
        return "Values of exchanges per currency";
    }

    @Override
    public GraphResult execute() {
        System.out.println("dupa");

        return new GraphResult(getName(), "Value of exchanges", "Currencies", getValuesToSurveyMap());
    }
    @Override
    public Map<String, Integer> getValuesToSurveyMap() {
//        return dbService.getCurrencyExchangePerValue();
        return currencyFormRepository.getCurrencyExchangePerValue();
    }
}