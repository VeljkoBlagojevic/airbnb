package rs.ac.bg.fon.airbnb_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.airbnb_backend.domain.Currency;
import rs.ac.bg.fon.airbnb_backend.repository.CurrencyRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/currencies")
@RequiredArgsConstructor
@CrossOrigin
public class CurrencyController {

    private final CurrencyRepository currencyRepository;

    @GetMapping
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

}
