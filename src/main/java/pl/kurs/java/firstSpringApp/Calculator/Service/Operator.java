package pl.kurs.java.firstSpringApp.Calculator.Service;

public interface Operator {
    double execute(int num1, int num2);

    String getName();
}
