package pl.kurs.java.firstSpringApp.Exchange.Service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.Rate;
import pl.kurs.java.firstSpringApp.Exchange.Model.Root;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class CurrencyExchangeServiceTest {
    @Mock
    private RestCurrencyApiService restCurrencyApiService;
    @Mock
    private DBService dbService;

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;


    //    @BeforeEach
//    public void setUp() {
//        currencyExchangeService = new CurrencyExchangeService(restCurrencyApiService, dbService);
//        MockitoAnnotations.openMocks(this);
//        System.out.println(restCurrencyApiService.getApiResponse());
//        when(restCurrencyApiService.getApiResponse()).thenReturn(createMockRoots());
//    }
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restCurrencyApiService.getApiResponse()).thenReturn(createMockRoots());
        currencyExchangeService = new CurrencyExchangeService(restCurrencyApiService, dbService);
        currencyExchangeService.getListCodeCurrencies();
    }


    @Test
    void getListCurrenciesCodeNullTest() {
        //TODO jak wywolac null root
//        List<String> listCodeCurrencies = currencyExchangeService.getListCodeCurrencies();
//        System.out.println(listCodeCurrencies);
    }

    @Test
    void getMidForCurrencyTest() {
        double mid = currencyExchangeService.getMidForCurrency("USD");
        System.out.println(mid);
        assertEquals(4.0714, mid, 0.0001);
    }

    @Test
    void getMidForCurrencyNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            currencyExchangeService.getMidForCurrency(null);
        });
    }


    @Test
    void exchangeTest1() {
        CurrencyExchangeForm currencyExchangeForm = new CurrencyExchangeForm("EUR", "USD", 100, 443.21);
        assertEquals(108.859, currencyExchangeService.exchange(currencyExchangeForm), 0.01);
    }

    @Test
    void exchangeNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CurrencyExchangeForm currencyExchangeForm = new CurrencyExchangeForm(null, "USD", 100, 443.21);
            currencyExchangeService.exchange(currencyExchangeForm);
        });
    }

    @Test
    void getValueInPLNTest() {
        CurrencyExchangeForm currencyExchangeForm = new CurrencyExchangeForm("EUR", "USD", 100, 443.21);
        double expected = 443.21;
        double actual = currencyExchangeService.getValueExchangeInPLN(currencyExchangeForm);
        Assertions.assertEquals(expected, actual, 0.01);
    }

    @Test
    void getValueInPLNNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CurrencyExchangeForm currencyExchangeForm = new CurrencyExchangeForm("EUR", null, 100, 443.21);
            currencyExchangeService.getValueExchangeInPLN(currencyExchangeForm);
        });
    }

    private Root createMockRoots() {
        List<Rate> rates = new ArrayList<>();
        rates.add(new Rate("dolar amerykański", "USD", 4.0714));
        rates.add(new Rate("euro", "EUR", 4.4321));
        return new Root("A", "126/A/NBP/2023", "2023-07-03", rates);
    }
}