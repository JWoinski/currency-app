package pl.kurs.java.firstSpringApp.Calculator.Service;

import org.springframework.stereotype.Component;
import pl.kurs.java.firstSpringApp.Calculator.Model.CalculatorForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OperatorFacade {
    private final Map<String, Operator> operators;

    public OperatorFacade(Set<Operator> allOperators) {
//        operators = new HashMap<>();
//        for (Operator operator : allOperators) {
//            operators.put(operator.getName(), operator);
//        }
        operators = allOperators.stream().collect(Collectors.toMap(Operator::getName, Function.identity()));
    }

    public double calculate(CalculatorForm calculatorForm) {
        return operators.get(calculatorForm.getOperator()).execute(calculatorForm.getNum1(), calculatorForm.getNum2());
    }

    public List<String> getOperatorsNames() {
        return new ArrayList<>(operators.keySet());
    }
}
