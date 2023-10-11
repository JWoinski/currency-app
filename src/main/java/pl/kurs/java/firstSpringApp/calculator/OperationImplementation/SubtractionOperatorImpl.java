package pl.kurs.java.firstSpringApp.calculator.OperationImplementation;

import org.springframework.stereotype.Service;
import pl.kurs.java.firstSpringApp.calculator.Service.Operator;

@Service
public class SubtractionOperatorImpl implements Operator {
    @Override
    public double execute(int num1, int num2) {
        return num1 - num2;
    }

    @Override
    public String getName() {
        return "-";
    }
}

/*

dodaj EP do opracji na walutach, czyli chcemy miec EP do wymiany waluty, ale dodatkowo chcemy zeby kazda operacja byla zapisywana w bazie danych

 */