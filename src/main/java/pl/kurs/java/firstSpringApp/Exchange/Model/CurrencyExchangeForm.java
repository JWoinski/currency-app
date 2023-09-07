package pl.kurs.java.firstSpringApp.Exchange.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyExchangeForm {
    private String currencyFrom;
    private String currencyTo;
    private int amount;
}
