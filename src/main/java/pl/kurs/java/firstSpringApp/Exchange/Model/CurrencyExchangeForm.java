package pl.kurs.java.firstSpringApp.Exchange.Model;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "exchange_details")
public class CurrencyExchangeForm {
    private String currencyFrom;
    private String currencyTo;
    private int amount;
    private double valueInPLN;
}
