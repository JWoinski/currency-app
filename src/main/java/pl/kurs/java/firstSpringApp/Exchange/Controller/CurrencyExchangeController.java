package pl.kurs.java.firstSpringApp.Exchange.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeForm;
import pl.kurs.java.firstSpringApp.Exchange.Model.CurrencyExchangeRatesForm;
import pl.kurs.java.firstSpringApp.Exchange.Service.CurrencyExchangeService;
import pl.kurs.java.firstSpringApp.Exchange.Service.RestCurrencyApiService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CurrencyExchangeController {
    private final CurrencyExchangeService currencyExchangeService;

    @GetMapping("/kantor")
    public String showExchangeForm(Model model) {
        CurrencyExchangeForm exchangeForm = new CurrencyExchangeForm();
        CurrencyExchangeRatesForm currencyExchangeRatesForm = new CurrencyExchangeRatesForm();
        List<String> currencies = currencyExchangeService.getListCodeCurrencies();
        boolean includePLN = true;

        model.addAttribute("currencies", currencies);
        model.addAttribute("includePLN", includePLN);
        model.addAttribute("exchangeForm", exchangeForm);
        model.addAttribute("exchangeRatesForm", currencyExchangeRatesForm);
        return "kantor";
    }

    @PostMapping("/exchange")
    public String exchange(Model model,
                           @ModelAttribute("ExchangeForm") CurrencyExchangeForm exchangeForm,
                           @ModelAttribute("ExchangeRatesForm") CurrencyExchangeRatesForm exchangeRatesForm) {
        List<String> currencies = currencyExchangeService.getListCodeCurrencies();
        boolean includePLN = true;

        model.addAttribute("currencies", currencies);
        model.addAttribute("includePLN", includePLN);
        model.addAttribute("exchangeForm", exchangeForm);
        model.addAttribute("exchangeRatesForm", exchangeRatesForm);
        model.addAttribute("result", currencyExchangeService.exchange(exchangeForm));
        return "kantor";
    }

    @GetMapping("/exchange-rates")
    public String exchangeRates(Model model,
                                @ModelAttribute("ExchangeRatesForm") CurrencyExchangeRatesForm exchangeRatesForm,
                                @ModelAttribute("ExchangeForm") CurrencyExchangeForm exchangeForm) {
        List<String> currencies = currencyExchangeService.getListCodeCurrencies();
        boolean includePLN = true;

        model.addAttribute("currencies", currencies);
        model.addAttribute("includePLN", includePLN);
        model.addAttribute("exchangeForm", exchangeForm);
        model.addAttribute("exchangeRatesForm", exchangeRatesForm);
        model.addAttribute("chartData", currencyExchangeService.getChartData(exchangeRatesForm));
        return "kantor";
    }
}