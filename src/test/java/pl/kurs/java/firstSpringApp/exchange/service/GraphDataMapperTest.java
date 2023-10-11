package pl.kurs.java.firstSpringApp.exchange.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static pl.kurs.java.firstSpringApp.exchange.service.GraphDataMapper.getMapFromObject;

class GraphDataMapperTest {
    @Test
    void getMapFromObjectTest() {
        Map<String, Integer> expected = Map.of("test1", 3, "test2", 5);
        Map<String, Integer> actual = getMapFromObject(List.of(
                new Object[]{"test1", 3}
                , new Object[]{"test2", 5}));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getMapFromObjectNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            getMapFromObject(null);
        });
    }

    @Test
    void getMapFromObjectEmptyListTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            getMapFromObject(Collections.emptyList());
        });
    }
}