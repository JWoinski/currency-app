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
//public class CurrencyExchangePerDayOfWeek implements ExchangeChart {
//    private final DBService dbService;
//
//    @Override
//    public String getName() {
//        return "Number of currency per day of week";
//    }
//
//    @Override
//    public GraphResult execute() {
//        return new GraphResult(getName(), "Number of Exchanges", "Days of week", getValuesToSurveyMap());
//    }
//
//    @Override
//    public Map<String, Integer> getValuesToSurveyMap() {
//        return dbService.getCurrencyExchangePerDayOfWeek();
//    }
//}