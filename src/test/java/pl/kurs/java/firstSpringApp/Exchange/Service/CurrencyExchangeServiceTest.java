package pl.kurs.java.firstSpringApp.Exchange.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.Rate;
import pl.kurs.java.firstSpringApp.Exchange.Model.Root;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class CurrencyExchangeServiceTest {
    @Mock
    private RestTemplate restTemplate;
    private Root[] mockRoots;
    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockRoots = createMockRoots();
        ResponseEntity<Root[]> mockResponse = new ResponseEntity<>(mockRoots, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(Root[].class))).thenReturn(mockResponse);
    }

    @Test
    void getListCurrenciesCode() {
        List<String> Expectedlist = List.of("EUR","USD");
        List<String> ActualList = currencyExchangeService.getListCodeCurrencies();
        Assertions.assertEquals(Expectedlist,ActualList);
    }

    @Test
    void getMidForCurrency() {
        double mid = currencyExchangeService.getMidForCurrency("USD");
        assertEquals(4.0714, mid, 0.0001);
    }

    @Test
    void exchange() {
        CurrencyExchangeForm currencyExchangeForm = new CurrencyExchangeForm("EUR","USD",100);
        assertEquals(108.859, currencyExchangeService.exchange(currencyExchangeForm), 0.01);
    }

    private Root[] createMockRoots() {
        List<Rate> rates = new ArrayList<>();
        rates.add(new Rate("dolar amerykański", "USD", 4.0714));
        rates.add(new Rate("euro", "EUR", 4.4321));
        Root root = new Root("A", "126/A/NBP/2023", "2023-07-03", rates);
        return new Root[] { root };
    }
}