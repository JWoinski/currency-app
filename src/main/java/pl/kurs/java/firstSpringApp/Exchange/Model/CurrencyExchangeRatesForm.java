package pl.kurs.java.firstSpringApp.Exchange.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeRatesForm {
    private String startDate;
    private String endDate;
    private String currency;
}