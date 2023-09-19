package pl.kurs.java.firstSpringApp.Exchange.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kurs.java.firstSpringApp.Exchange.Model.GraphForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.GraphResult;
import pl.kurs.java.firstSpringApp.Exchange.Service.GraphFacade;

import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/graph")
public class GraphController {
    private final GraphFacade graphFacade;

    @GetMapping
    public String showGraphForm(Model model) {
        model.addAttribute("charts", graphFacade.getChartNames());
        model.addAttribute("graphForm", new GraphForm());
        return "graphForm";
    }

    @PostMapping("/execute")
    public String graphExecute(Model model, @ModelAttribute("GraphForm") GraphForm graphForm) throws SQLException, ClassNotFoundException {
        String name = graphForm.getName();
        GraphResult graphResult = graphFacade.getResult(graphForm);
        model.addAttribute("titleString", graphResult.getTitleString());
        model.addAttribute("yAxis", graphResult.getYAxis());
        model.addAttribute("seriesName", graphResult.getSeriesName());
        model.addAttribute("surveyMap", graphResult.getSurveyMap());
        return "graphExecute";
    }
}