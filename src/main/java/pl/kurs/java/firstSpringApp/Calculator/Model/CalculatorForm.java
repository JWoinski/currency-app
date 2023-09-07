package pl.kurs.java.firstSpringApp.Calculator.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kurs.java.firstSpringApp.Calculator.Service.Operator;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorForm {
    private int num1;
    private int num2;
    private String operator;
}
