package pl.kurs.java.firstSpringApp.Exchange.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Root{
    private String table;
    private String no;
    private String effectiveDate;
    private List<Rate> rates;
}