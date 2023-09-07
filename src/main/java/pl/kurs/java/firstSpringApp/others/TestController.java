package pl.kurs.java.firstSpringApp.others;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping("/test")
    public String testEp(ModelMap modelMap) {
        modelMap.addAttribute("testVar", "to jest moja wartosc");
        return "testHtml";
    }
}
