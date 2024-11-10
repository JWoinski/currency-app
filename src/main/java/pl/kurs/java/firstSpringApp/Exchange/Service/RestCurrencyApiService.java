package pl.kurs.java.firstSpringApp.Exchange.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kurs.java.firstSpringApp.Exchange.Model.Rate;
import pl.kurs.java.firstSpringApp.Exchange.Model.Root;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestCurrencyApiService {
    private static final String URL = "http://api.nbp.pl/api/exchangerates/tables/a/?format=json";
    private static final String CURRENCY_URL = "https://api.nbp.pl/api/exchangerates/rates/a/";
    private final RestTemplate restTemplate;

    public Root getApiResponse() {
        ResponseEntity<Root[]> exchange = restTemplate.exchange(URL, HttpMethod.GET, null, Root[].class);
        return Optional.ofNullable(exchange.getBody())
                .flatMap(roots -> Arrays.stream(roots).findFirst())
                .filter(this::validate)
                .orElseThrow(() -> new IllegalStateException("RootValidateException"));
    }

    public Root getApiResponse(String currency, String startDate, String endDate) {
        String currencyUrl = getCurrencyUrl(currency, startDate, endDate);
        ResponseEntity<Root> exchange = restTemplate.exchange(currencyUrl, HttpMethod.GET, null, Root.class);
        return Optional.ofNullable(exchange.getBody())
                .filter(this::validate)
                .orElseThrow(() -> new IllegalStateException("RootValidateException"));
    }

    String getCurrencyUrl(String currency, String startDate, String endDate) {
        return CURRENCY_URL + currency + "/" + startDate + "/" + endDate + "/?format=json";
    }

    private boolean validate(Root root) {
        List<String> codeList = Optional.ofNullable(root)
                .map(Root::getRates)
                .stream()
                .flatMap(rates -> rates.stream().map(Rate::getCode))
                .toList();
        return !codeList.isEmpty();
    }
}