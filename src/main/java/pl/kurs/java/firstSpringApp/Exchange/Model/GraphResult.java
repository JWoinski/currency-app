package pl.kurs.java.firstSpringApp.Exchange.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphResult {
    private String titleString;
    private String yAxis;
    private String seriesName;
    private Map<String, Integer> surveyMap;
}