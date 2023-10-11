package pl.kurs.java.firstSpringApp.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.java.firstSpringApp.exchange.model.GraphResult;
import pl.kurs.java.firstSpringApp.exchange.operationChart.ExchangeChart;
import pl.kurs.java.firstSpringApp.exchange.service.dataBaseService.CurrencyFormRepository;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class CurrencyExchangeTestImpl implements ExchangeChart {
    @Override
    public String getName() {
        return "Test Chart";
    }

    @Override
    public GraphResult execute() {
        return new GraphResult(getName(), "Y-Axis", "X-Axis", getValuesToSurveyMap());
    }

    public Map<String, Integer> getValuesToSurveyMap() {
        return Map.of("test1",2,"test2",3);
    }
}
