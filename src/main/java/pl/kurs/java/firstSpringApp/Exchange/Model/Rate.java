package pl.kurs.java.firstSpringApp.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rate {
    private String currency;
    private String code;
    private double mid;
}