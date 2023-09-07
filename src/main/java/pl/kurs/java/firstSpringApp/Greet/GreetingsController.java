package pl.kurs.java.firstSpringApp.Greet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingsController {

    @GetMapping("/greet")
    public String showGreetingForm(Model model) {
        String initialGreet = "Hello, what's your name?";
        model.addAttribute("greet", initialGreet); // Add the initial greeting
        return "greet";
    }

    @PostMapping("/greet")
    public String showGreeting(@RequestParam String name, Model model) {
        String personalizedGreet = "Hello, " + name + "!";
        model.addAttribute("name", name);
        model.addAttribute("greet", personalizedGreet); // Add the personalized greeting
        return "greet";
    }
}


