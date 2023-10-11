package pl.kurs.java.firstSpringApp.testJpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String testValue;
    private boolean testBooleanValue;

    public TestEntity(String testValue, boolean testBooleanValue) {
        this.testValue = testValue;
        this.testBooleanValue = testBooleanValue;
    }
}
