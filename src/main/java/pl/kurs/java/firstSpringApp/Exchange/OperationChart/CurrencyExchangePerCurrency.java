//package pl.kurs.java.firstSpringApp.Exchange.OperationChart;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import pl.kurs.java.firstSpringApp.Exchange.Model.GraphResult;
//import pl.kurs.java.firstSpringApp.Exchange.Service.DBService;
//import pl.kurs.java.firstSpringApp.Exchange.Service.ExchangeChart;
//
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class CurrencyExchangePerCurrency implements ExchangeChart {
//    private final DBService dbService; // PAMIETAC O REQUIREDARGSCONSTRUCTOR
//
//    @Override
//    public String getName() {
//        return "Number of exchanges per currency";
//    }
//
//    @Override
//    public GraphResult execute() {
//        return new GraphResult(getName(), "Values", "Number of exchanges", getValuesToSurveyMap());
//    }
//
//    public Map<String, Integer> getValuesToSurveyMap() {
//        return dbService.getCurrencyExchangePerCurrency();
//    }
//}