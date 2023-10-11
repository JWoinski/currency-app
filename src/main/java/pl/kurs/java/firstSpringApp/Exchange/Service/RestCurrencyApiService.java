package pl.kurs.java.firstSpringApp.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kurs.java.firstSpringApp.exchange.model.Rate;
import pl.kurs.java.firstSpringApp.exchange.model.Root;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestCurrencyApiService {
    private final RestTemplate restTemplate;

    private static final String URL = "http://api.nbp.pl/api/exchangerates/tables/a/?format=json";

    public Root getApiResponse() {
        ResponseEntity<Root[]> exchange = restTemplate.exchange(URL, HttpMethod.GET, null, Root[].class);
        return Optional.ofNullable(exchange.getBody())
                .flatMap(roots -> Arrays.stream(roots).findFirst())
                .filter(this::validate)
                .orElseThrow(() -> new IllegalStateException("RootValidateException"));
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