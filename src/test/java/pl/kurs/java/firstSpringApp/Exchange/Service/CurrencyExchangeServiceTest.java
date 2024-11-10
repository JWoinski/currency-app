package pl.kurs.java.firstSpringApp.Exchange.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeRatesForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.Rate;
import pl.kurs.java.firstSpringApp.Exchange.Model.Root;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyExchangeServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestCurrencyApiService restCurrencyApiService;

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    private Root mockRoot;

    @BeforeEach
    void setUp() {
        // Create mock Rate objects
        Rate usdRate = new Rate("USD", "USD", 4.25, "2024-11-10");
        Rate eurRate = new Rate("EUR", "EUR", 4.80, "2024-11-10");

        // Create mock Root object containing mock rates
        mockRoot = new Root();
        mockRoot.setRates(List.of(usdRate, eurRate));
    }

    @Test
    void testGetListCodeCurrencies() {
        // Arrange
        when(restCurrencyApiService.getApiResponse()).thenReturn(mockRoot);

        // Act
        List<String> result = currencyExchangeService.getListCodeCurrencies();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("USD"));
        assertTrue(result.contains("EUR"));
        assertTrue(result.contains("PLN"));
        verify(restCurrencyApiService, times(1)).getApiResponse();
    }

    @Test
    void testGetMidForCurrency_success() {
        // Arrange
        when(restCurrencyApiService.getApiResponse()).thenReturn(mockRoot);
        // Act
        double usdMid = currencyExchangeService.getMidForCurrency("USD");
        double eurMid = currencyExchangeService.getMidForCurrency("EUR");

        // Assert
        assertEquals(4.25, usdMid);
        assertEquals(4.80, eurMid);
        verify(restCurrencyApiService, times(1)).getApiResponse();
    }

    @Test
    void testGetMidForCurrency_currencyNotFound() {
        // Arrange
        when(restCurrencyApiService.getApiResponse()).thenReturn(mockRoot);

        // Act
        double result = currencyExchangeService.getMidForCurrency("GBP");

        // Assert
        assertEquals(0.0, result);
        verify(restCurrencyApiService, times(1)).getApiResponse();
    }

    @Test
    void testExchange_sameCurrency() {
        // Arrange
        CurrencyExchangeForm form = new CurrencyExchangeForm("USD", "USD", 100);
        // Act
        double result = currencyExchangeService.exchange(form);

        // Assert
        assertEquals(100.0, result);
        verify(restCurrencyApiService, times(0)).getApiResponse();
    }

    @Test
    void testExchange_PLNToCurrency() {
        // Arrange
        CurrencyExchangeForm form = new CurrencyExchangeForm("PLN", "USD", 100);
        when(restCurrencyApiService.getApiResponse()).thenReturn(mockRoot);

        // Act
        double result = currencyExchangeService.exchange(form);

        // Assert
        assertEquals(100 / 4.25, result); // Amount in PLN divided by the USD mid rate
        verify(restCurrencyApiService, times(1)).getApiResponse();
    }

    @Test
    void testExchange_CurrencyToPLN() {
        // Arrange
        CurrencyExchangeForm form = new CurrencyExchangeForm("USD", "PLN", 100);
        when(restCurrencyApiService.getApiResponse()).thenReturn(mockRoot);

        // Act
        double result = currencyExchangeService.exchange(form);

        // Assert
        assertEquals(100 * 4.25, result); // Amount in USD multiplied by the USD mid rate
        verify(restCurrencyApiService, times(1)).getApiResponse();
    }

    @Test
    void testGetValueExchangeInPLN() {
        // Arrange
        CurrencyExchangeForm form = new CurrencyExchangeForm("USD", "PLN", 100);
        when(restCurrencyApiService.getApiResponse()).thenReturn(mockRoot);

        // Act
        double result = currencyExchangeService.getValueExchangeInPLN(form);

        // Assert
        assertEquals(100 * 4.25, result); // Amount in USD converted to PLN
        verify(restCurrencyApiService, times(1)).getApiResponse();
    }

    @Test
    void testGetExchangeRatesForDatePeriod_invalidDate() {
        // Arrange
        CurrencyExchangeRatesForm form = new CurrencyExchangeRatesForm("2024-12-01", "2024-11-01","USD"); // Invalid date range

        // Act
        List<Rate> result = currencyExchangeService.getExchangeRatesForDatePeriod(form);

        // Assert
        assertTrue(result.isEmpty()); // Return an empty list due to invalid date range
    }

    @Test
    void testGetExchangeRatesForDatePeriod_validDate() {
        // Arrange
        CurrencyExchangeRatesForm form = new CurrencyExchangeRatesForm("2024-01-01", "2024-11-10", "USD");

        // Zamockowanie odpowiedzi z RestTemplate
        Root mockRoot = createMockRoot();

        when(restCurrencyApiService.getApiResponse("USD", "2024-01-01", "2024-11-10"))
                .thenReturn(mockRoot);
        // Act
        List<Rate> result = currencyExchangeService.getExchangeRatesForDatePeriod(form);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(2, result.size()); // Spodziewamy się 2 kursow
        verify(restCurrencyApiService, times(1)).getApiResponse("USD", "2024-01-01", "2024-11-10");
    }
    private Root createMockRoot() {
        Rate usdRate = new Rate("USD", "USD", 4.21, "2024-11-08");
        Rate usdRate2 = new Rate("USD", "USD", 4.25, "2024-11-09");
        Root mockRoot = new Root();
        mockRoot.setRates(List.of(usdRate,usdRate2));
        return mockRoot;
    }
    @Test
    void testGetChartData_validData() {
        // Arrange
        CurrencyExchangeRatesForm form = new CurrencyExchangeRatesForm("2024-01-01", "2024-11-10","USD" );
        when(restCurrencyApiService.getApiResponse("USD","2024-01-01", "2024-11-10")).thenReturn(createMockRoot());

        // Act
        String result = currencyExchangeService.getChartData(form);
        // Assert
        assertNotNull(result);
        assertTrue(result.contains("\"labels\": [\"2024-11-08\",\"2024-11-09\"]"));
        assertTrue(result.contains("\"data\": [4.21,4.25] }"));
    }
}