package pl.kurs.java.firstSpringApp.exchange.operationChart;

import pl.kurs.java.firstSpringApp.exchange.model.GraphResult;

import java.util.Map;

public interface ExchangeChart {
    String getName();

    GraphResult execute();

    Map<String, Integer> getValuesToSurveyMap();
}