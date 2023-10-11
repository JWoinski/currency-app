package pl.kurs.java.firstSpringApp.exchange.service.dataBaseService;

import org.springframework.stereotype.Service;
import pl.kurs.java.firstSpringApp.exchange.model.CurrencyExchangeForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static java.sql.DriverManager.getConnection;

@Service
public class DBService {
    private final Connection connection;

    public DBService() throws SQLException {
        final String url = "jdbc:mysql://localhost:3306/exchange_office";
        final String username = "root";
        final String password = "rootroot";
        connection = getConnection(url, username, password);
    }

    public void saveDetailsOfExchangeToDB(CurrencyExchangeForm exchangeForm, double valueExchangeInPLN) {
        String sql = "INSERT INTO exchange_details (currency_from, currency_to, amount, value_in_pln, exchange_date) VALUES (?, ?, ?, ?, NOW())";
        if (exchangeForm == null || exchangeForm.getCurrencyFrom() == null || exchangeForm.getCurrencyTo() == null) {
            throw new IllegalArgumentException("ExchangeForm is null");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, exchangeForm.getCurrencyFrom());
            preparedStatement.setString(2, exchangeForm.getCurrencyTo());
            preparedStatement.setInt(3, exchangeForm.getAmount());
            preparedStatement.setDouble(4, valueExchangeInPLN);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getCurrencyExchangePerCurrency() {
        Map<String, Integer> chartsMap = new HashMap<>();
        try {
            final String sql = "SELECT currency_name, COUNT(*) as exchange_count\n" +
                    "FROM (\n" +
                    "    SELECT currency_from as currency_name FROM exchange_details\n" +
                    "    UNION ALL\n" +
                    "    SELECT currency_to as currency_name FROM exchange_details\n" +
                    ") AS combined_currencies\n" +
                    "GROUP BY currency_name\n" +
                    "ORDER BY exchange_count DESC;;";
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);

            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final int numberOfExchanges = resultSet.getInt("exchange_count");
                final String currency = resultSet.getString("currency_name");
                chartsMap.put(currency, numberOfExchanges);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return chartsMap;
    }

    public Map<String, Integer> getCurrencyExchangePerDayOfWeek() {
        Map<String, Integer> chartsMap = new HashMap<>();
        try {
            final String sql = "SELECT DAYNAME(exchange_date) AS day_of_week, COUNT(*) AS exchange_count\n" +
                    "FROM exchange_details\n" +
                    "GROUP BY day_of_week\n" +
                    "ORDER BY day_of_week;\n";
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);

            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int numberOfExchanges = resultSet.getInt("exchange_count");
                final String dayOfWeek = resultSet.getString("day_of_week");
                chartsMap.put(dayOfWeek, numberOfExchanges);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chartsMap;
    }

    public Map<String, Integer> getCurrencyExchangePerHour() {
        Map<String, Integer> chartsMap = new HashMap<>();
        try {
            final String sql = "SELECT HOUR(exchange_date) AS hour_of_day, COUNT(*) AS exchange_count\n" +
                    "FROM exchange_details\n" +
                    "GROUP BY hour_of_day\n" +
                    "ORDER BY hour_of_day;\n";
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);

            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int numberOfExchanges = resultSet.getInt("exchange_count");
                final String hour = resultSet.getString("hour_of_day");
                chartsMap.put(hour, numberOfExchanges);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chartsMap;
    }

    public Map<String, Integer> getCurrencyExchangePerValue() {
        Map<String, Integer> chartsMap = new HashMap<>();
        try {
            final String sql = "SELECT currency_name, SUM(value_in_pln) as value_sum\n" +
                    "FROM (\n" +
                    "    SELECT currency_from as currency_name, value_in_pln FROM exchange_details\n" +
                    "    UNION ALL\n" +
                    "    SELECT currency_to as currency_name, value_in_pln FROM exchange_details\n" +
                    ") AS combined_currencies\n" +
                    "GROUP BY currency_name\n" +
                    "ORDER BY value_sum DESC;\n";
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);

            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final int valuesOfExchanges = resultSet.getInt("value_sum");
                final String currency = resultSet.getString("currency_name");
                chartsMap.put(currency, valuesOfExchanges);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chartsMap;
    }
}