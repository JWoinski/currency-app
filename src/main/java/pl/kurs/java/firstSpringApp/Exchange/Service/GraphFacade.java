package pl.kurs.java.firstSpringApp.Exchange.Service;

import org.springframework.stereotype.Component;
import pl.kurs.java.firstSpringApp.Exchange.Model.GraphForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.GraphResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GraphFacade {
    Map<String, ExchangeChart> exchangeChartMap;

    public GraphFacade(Set<ExchangeChart> allCharts) {
        exchangeChartMap = allCharts
                .stream()
                .collect(Collectors.toMap(ExchangeChart::getName, Function.identity()));
    }

    public GraphResult getResult(GraphForm graphForm) {
        ExchangeChart exchangeChart = exchangeChartMap.get(graphForm.getName());

        if (exchangeChart != null) {
            return exchangeChart.execute();
        } else {
            throw new IllegalArgumentException("Invalid exchange chart title: " + graphForm.getName());
        }
    }

    public List<String> getChartNames() {
        return new ArrayList<>(exchangeChartMap.keySet());
    }

}