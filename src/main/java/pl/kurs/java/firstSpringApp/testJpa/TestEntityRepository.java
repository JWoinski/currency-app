package pl.kurs.java.firstSpringApp.testJpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity, Integer> {
    TestEntity findByTestValue(String testValue);

}
