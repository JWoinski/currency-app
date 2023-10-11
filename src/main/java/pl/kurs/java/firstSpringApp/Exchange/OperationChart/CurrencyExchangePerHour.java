package pl.kurs.java.firstSpringApp.exchange.operationChart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.java.firstSpringApp.exchange.model.GraphResult;
import pl.kurs.java.firstSpringApp.exchange.service.dataBaseService.CurrencyFormRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyExchangePerHour implements ExchangeChart {
    //    private final DBService dbService;
    private final CurrencyFormRepository currencyFormRepository;

    @Override
    public String getName() {
        return "Number of exchanges per hour";
    }

    @Override
    public GraphResult execute() {

        return new GraphResult(getName(), "Number of exchanges", "Hour of exchanges", getValuesToSurveyMap());
    }

    @Override
    public Map<String, Integer> getValuesToSurveyMap() {
//        return dbService.getCurrencyExchangePerHour();
        return currencyFormRepository.getCurrencyExchangePerHour();
    }
}