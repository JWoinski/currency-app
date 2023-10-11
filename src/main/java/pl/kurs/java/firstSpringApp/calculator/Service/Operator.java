package pl.kurs.java.firstSpringApp.calculator.Service;

public interface Operator {
    double execute(int num1, int num2);

    String getName();
}
