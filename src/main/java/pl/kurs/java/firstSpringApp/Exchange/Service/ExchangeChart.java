package pl.kurs.java.firstSpringApp.Exchange.Service;

import pl.kurs.java.firstSpringApp.Exchange.Model.GraphResult;

import java.util.Map;

public interface ExchangeChart {
    String getName();

    GraphResult execute();

    Map<String, Integer> getValuesToSurveyMap();
}