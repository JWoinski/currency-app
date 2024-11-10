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
//public class CurrencyExchangePerValue implements ExchangeChart {
//    private final DBService dbService;
//
//    @Override
//    public String getName() {
//        return "Values of exchanges per currency";
//    }
//
//    @Override
//    public GraphResult execute() {
//
//        return new GraphResult(getName(), "Value of exchanges", "Currencies", getValuesToSurveyMap());
//    }
//
//    @Override
//    public Map<String, Integer> getValuesToSurveyMap() {
//        return dbService.getCurrencyExchangePerValue();
//
//    }
//}
