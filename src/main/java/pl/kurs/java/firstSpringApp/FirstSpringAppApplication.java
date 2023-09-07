package pl.kurs.java.firstSpringApp;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.kurs.java.firstSpringApp.Exchange.Model.Root;
import pl.kurs.java.firstSpringApp.Exchange.Service.DBService;

import java.sql.SQLException;


@SpringBootApplication
public class FirstSpringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstSpringAppApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	@Bean
	@SneakyThrows
	public DBService dbService() throws SQLException {
		return new DBService();
	}
	public Root root(){
		return new Root();
	}

}
