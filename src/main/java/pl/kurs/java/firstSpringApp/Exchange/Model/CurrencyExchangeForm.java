package pl.kurs.java.firstSpringApp.exchange.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kurs.java.firstSpringApp.exchange.service.dataBaseService.DateHelper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exchange_details")
public class CurrencyExchangeForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    private int id;
    private String currencyFrom;
    private String currencyTo;
    private int amount;
    @Column(name = "value_in_pln")
    private double valueInPln;
    private String exchangeDate;

    public CurrencyExchangeForm(String currencyFrom, String currencyTo, int amount, double valueInPLN) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amount = amount;
        this.valueInPln = valueInPLN;
        exchangeDate = DateHelper.getActualDate();
    }
}