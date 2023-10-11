package pl.kurs.java.firstSpringApp.calculator.OperationImplementation;

import org.springframework.stereotype.Service;
import pl.kurs.java.firstSpringApp.calculator.Service.Operator;

@Service
public class DivideOperatorImpl implements Operator {
    @Override
    public double execute(int num1, int num2) {
        return (num2 == 0) ? 0 : num1 / num2;
    }

    @Override
    public String getName() {
        return "/";
    }
}
