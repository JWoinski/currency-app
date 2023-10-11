package pl.kurs.java.firstSpringApp.calculator.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorForm {
    private int num1;
    private int num2;
    private String operator;
}
