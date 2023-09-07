package pl.kurs.java.firstSpringApp.Exchange.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeForm;
import pl.kurs.java.firstSpringApp.Exchange.Service.CurrencyExchangeService;

@Controller
@RequiredArgsConstructor
public class CurrencyExchangeController {
    private final CurrencyExchangeService currencyExchangeService;

    @GetMapping("/kantor")
    public String showExchangeForm(Model model) {
        CurrencyExchangeForm exchangeForm = new CurrencyExchangeForm();
        model.addAttribute("currencies", currencyExchangeService.getListCodeCurrencies());
        model.addAttribute("exchangeForm", exchangeForm);
        return "kantor";
    }

    @PostMapping("/exchange")
    public String exchange(Model model, @ModelAttribute("ExchangeForm") CurrencyExchangeForm exchangeForm) {
        model.addAttribute("currencies", currencyExchangeService.getListCodeCurrencies());
        model.addAttribute("exchangeForm", exchangeForm);
        model.addAttribute("result", currencyExchangeService.exchange(exchangeForm));
        return "kantor";
    }
}
