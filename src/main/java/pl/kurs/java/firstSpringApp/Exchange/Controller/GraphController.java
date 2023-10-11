package pl.kurs.java.firstSpringApp.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kurs.java.firstSpringApp.exchange.model.GraphForm;
import pl.kurs.java.firstSpringApp.exchange.model.GraphResult;
import pl.kurs.java.firstSpringApp.exchange.service.CurrencyExchangeService;
import pl.kurs.java.firstSpringApp.exchange.service.GraphFacade;

import java.sql.SQLException;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/graph", method = RequestMethod.GET)
public class GraphController {
    private final GraphFacade graphFacade;
    private final CurrencyExchangeService currencyExchangeService;

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