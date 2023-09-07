package pl.kurs.java.firstSpringApp.Exchange.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeForm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

@Service
@RequiredArgsConstructor
public class DBService {
    private final Connection connection;

    public DBService() throws SQLException, ClassNotFoundException {
        final String url = "jdbc:mysql://localhost:3306/exchange_office";
        final String username = "root";
        final String password = "rootroot";
        connection = getConnection(url, username, password);
    }

    public void saveDetailsOfExchangeToDB(CurrencyExchangeForm exchangeForm) {
        String sql = "INSERT INTO exchange_details (currency_from, currency_to, amount, exchange_date) VALUES (?, ?, ?, NOW())";
        if(exchangeForm==null || exchangeForm.getCurrencyFrom() == null || exchangeForm.getCurrencyTo() == null){
            throw new IllegalArgumentException("ExchangeForm is null");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, exchangeForm.getCurrencyFrom());
            preparedStatement.setString(2, exchangeForm.getCurrencyTo());
            preparedStatement.setInt(3, exchangeForm.getAmount());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Exchange details saved successfully!");
            } else {
                System.out.println("Failed to save exchange details.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
