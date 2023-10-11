package pl.kurs.java.firstSpringApp.exchange.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kurs.java.firstSpringApp.exchange.model.GraphForm;
import pl.kurs.java.firstSpringApp.exchange.model.GraphResult;
import pl.kurs.java.firstSpringApp.exchange.operationChart.ExchangeChart;
import pl.kurs.java.firstSpringApp.exchange.service.dataBaseService.CurrencyFormRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GraphFacadeTest {
    private  CurrencyExchangeTestImpl currencyExchangeTest;

    private GraphFacade graphFacade;


    @BeforeEach
    public void setUp() {
        Set<ExchangeChart> allCharts = new HashSet<>();
        currencyExchangeTest = new CurrencyExchangeTestImpl();
        allCharts.add(currencyExchangeTest);
        graphFacade = new GraphFacade(allCharts);
    }

    @Test
    public void testGetResult() {
        GraphForm graphForm = new GraphForm("Test Chart");
        GraphResult result = graphFacade.getResult(graphForm);
        assertNotNull(result);
        assertEquals("Test Chart", result.getTitleString());
    }

    @Test
    public void testGetChartNames() {
        List<String> chartNames = graphFacade.getChartNames();
        assertNotNull(chartNames);
        assertTrue(chartNames.contains("Test Chart"));
    }

    @Test
    public void testInvalidChartName() {
        GraphForm graphForm = new GraphForm("Invalid Chart");
        assertThrows(IllegalArgumentException.class, () -> graphFacade.getResult(graphForm));
    }
}
