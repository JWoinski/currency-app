package pl.kurs.java.firstSpringApp.exchange.service;

import java.util.*;
import java.util.stream.Collectors;

public class GraphDataMapper {
    public static Map<String, Integer> getMapFromObject(List<Object[]> objectList) {
        if(objectList==null){
            throw new IllegalArgumentException("List cannot be null");
        }
        if(objectList.isEmpty()){
            throw new IllegalArgumentException("List cannot be empty");
        }
        return objectList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        row -> row[0].toString(),
                        row -> ((Number) row[1]).intValue()));
    }
}