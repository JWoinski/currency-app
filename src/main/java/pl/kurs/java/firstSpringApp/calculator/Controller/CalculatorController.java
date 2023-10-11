package pl.kurs.java.firstSpringApp.calculator.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kurs.java.firstSpringApp.calculator.Model.CalculatorForm;
import pl.kurs.java.firstSpringApp.calculator.Service.OperatorFacade;

@Controller
@RequiredArgsConstructor
public class CalculatorController {
    private final OperatorFacade operatorFacade;

    @GetMapping("/calculator")
    public String showCalculatorForm(Model model) {
        CalculatorForm calculatorForm = new CalculatorForm();
        model.addAttribute("operators", operatorFacade.getOperatorsNames());
        model.addAttribute("calculatorForm", calculatorForm);
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(Model model, @ModelAttribute("calculatorForm") CalculatorForm calculatorForm) {
//        Map<String, Function<CalculatorForm, Double>> functionMap = Map.of(
//                "+", x -> (double) x.getNum1() + x.getNum2(),
//                "-", x -> (double) x.getNum1() - x.getNum2(),
//                "*", x -> (double) x.getNum1() * x.getNum2(),
//                "/", x -> (double) x.getNum1() / x.getNum2()
//        );
//        double result = functionMap.get(calculatorForm.getOperator()).apply(calculatorForm);
        model.addAttribute("operators", operatorFacade.getOperatorsNames());
        model.addAttribute("result", operatorFacade.calculate(calculatorForm));
        return "calculator";
    }
}

