package pl.kurs.java.firstSpringApp.exchange.service.dataBaseService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.java.firstSpringApp.exchange.model.CurrencyExchangeForm;
import pl.kurs.java.firstSpringApp.exchange.service.GraphDataMapper;

import java.util.List;
import java.util.Map;

public interface CurrencyFormRepository extends JpaRepository<CurrencyExchangeForm, Integer> {

    @Query(nativeQuery = true, value = "SELECT currency_name, COUNT(*) as exchange_count FROM ( SELECT currency_from as currency_name FROM exchange_details UNION ALL SELECT currency_to as currency_name FROM exchange_details) AS combined_currencies GROUP BY currency_name ORDER BY exchange_count DESC;;")
    List<Object[]> getCurrencyExchangePerCurrencyRaw();

    default Map<String, Integer> getCurrencyExchangePerCurrency() {
        return GraphDataMapper.getMapFromObject(getCurrencyExchangePerCurrencyRaw());

    }

    @Query(nativeQuery = true, value =
            "SELECT DAYNAME(exchange_date) AS day_of_week, COUNT(*) AS exchange_count FROM exchange_details GROUP BY day_of_week ")
    List<Object[]> getCurrencyExchangePerDayOfWeekRaw();

    default Map<String, Integer> getCurrencyExchangePerDayOfWeek() {
        return GraphDataMapper.getMapFromObject(getCurrencyExchangePerDayOfWeekRaw());
    }

    @Query(nativeQuery = true, value =
            "SELECT HOUR(exchange_date) AS hour_of_day, COUNT(*) AS exchange_count FROM exchange_details GROUP BY hour_of_day ")
    List<Object[]> getCurrencyExchangePerHourRaw();

    default Map<String, Integer> getCurrencyExchangePerHour() {
        return GraphDataMapper.getMapFromObject(getCurrencyExchangePerHourRaw());
    }

    @Query(nativeQuery = true, value =
            "SELECT currency_name, SUM(value_in_pln) as value_sum FROM (SELECT currency_from as currency_name, value_in_pln FROM exchange_details UNION ALL SELECT currency_to as currency_name, value_in_pln FROM exchange_details) AS combined_currencies GROUP BY currency_name ORDER BY value_sum DESC;")
    List<Object[]> getCurrencyExchangePerValueRaw();

    default Map<String, Integer> getCurrencyExchangePerValue() {
        return GraphDataMapper.getMapFromObject(getCurrencyExchangePerValueRaw());
    }

}