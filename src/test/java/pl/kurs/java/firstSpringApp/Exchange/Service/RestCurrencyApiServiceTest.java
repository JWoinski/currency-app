package pl.kurs.java.firstSpringApp.Exchange.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.kurs.java.firstSpringApp.Exchange.Model.Rate;
import pl.kurs.java.firstSpringApp.Exchange.Model.Root;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RestCurrencyApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestCurrencyApiService restCurrencyApiService;

    private Root mockRoot;

    @BeforeEach
    void setUp() {
        Rate rate = new Rate();
        rate.setCode("USD");
        rate.setMid(4.35);
        mockRoot = new Root();
        mockRoot.setRates(List.of(rate));
    }

    @Test
    void testGetApiResponse_success() {
        // Arrange
        Root[] rootArray = new Root[]{mockRoot};
        ResponseEntity<Root[]> responseEntity = ResponseEntity.ok(rootArray);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(Root[].class)))
                .thenReturn(responseEntity);

        // Act
        Root result = restCurrencyApiService.getApiResponse();

        // Assert
        assertNotNull(result);
        assertEquals("USD", result.getRates().get(0).getCode());
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(Root[].class));
    }

    @Test
    void testGetApiResponse_invalidResponse() {
        // Arrange
        ResponseEntity<Root[]> responseEntity = ResponseEntity.ok(new Root[0]);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(Root[].class)))
                .thenReturn(responseEntity);

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            restCurrencyApiService.getApiResponse();
        });
        assertEquals("RootValidateException", thrown.getMessage());
    }

    @Test
    void testGetApiResponseWithParams_success() {
        // Arrange
        ResponseEntity<Root> responseEntity = ResponseEntity.ok(mockRoot);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(Root.class)))
                .thenReturn(responseEntity);

        // Act
        Root result = restCurrencyApiService.getApiResponse("USD", "2024-01-01", "2024-11-01");

        // Assert
        assertNotNull(result);
        assertEquals("USD", result.getRates().get(0).getCode());
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(Root.class));
    }

    @Test
    void testGetApiResponseWithParams_invalidResponse() {
        // Arrange
        ResponseEntity<Root> responseEntity = ResponseEntity.ok(new Root());
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(Root.class)))
                .thenReturn(responseEntity);

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            restCurrencyApiService.getApiResponse("USD", "2024-01-01", "2024-11-01");
        });
        assertEquals("RootValidateException", thrown.getMessage());
    }

    @Test
    void testGetCurrencyUrl() {
        // Act
        String result = restCurrencyApiService.getCurrencyUrl("USD", "2024-01-01", "2024-11-01");

        // Assert
        String expectedUrl = "https://api.nbp.pl/api/exchangerates/rates/a/USD/2024-01-01/2024-11-01/?format=json";
        assertEquals(expectedUrl, result);
    }
}
