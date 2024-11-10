package pl.kurs.java.firstSpringApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


//    @Bean
//    public Connection connection() throws SQLException {
//        final String url = "jdbc:mysql://localhost:3306/exchange_office";
//        final String username = "root";
//        final String password = "rootroot#123";
//        return getConnection(url, username, password);
//    }
}
