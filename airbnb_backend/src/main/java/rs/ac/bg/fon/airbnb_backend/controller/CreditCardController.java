package rs.ac.bg.fon.airbnb_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.airbnb_backend.domain.CreditCard;
import rs.ac.bg.fon.airbnb_backend.repository.CreditCardRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/creditCards")
@RequiredArgsConstructor
@CrossOrigin
public class CreditCardController {

    private final CreditCardRepository creditCardRepository;

    @GetMapping
    public List<CreditCard> findAll() {
        return creditCardRepository.findAll();
    }

    @PostMapping
    public void save(@RequestBody CreditCard creditCard) {
        creditCardRepository.save(creditCard);
    }

    @PostMapping("/withUserName")
    public void saveWithUserName(@RequestBody CreditCard creditCard) {
        creditCardRepository.saveWithUserName(creditCard);
    }

    @DeleteMapping("{creditCardNumber}")
    public void delete(@PathVariable String creditCardNumber) {
        creditCardRepository.delete(creditCardNumber);
    }

    @PatchMapping("{creditCardNumber}")
    public void update(@PathVariable String creditCardNumber, @RequestBody CreditCard creditCard) {
        creditCardRepository.update(creditCardNumber, creditCard);
    }

    @PatchMapping("{creditCardNumber}/withUserName")
    public void updateWithUserName(@PathVariable String creditCardNumber, @RequestBody CreditCard creditCard) {
        creditCardRepository.updateWithUserName(creditCardNumber, creditCard);
    }

    @GetMapping("expiryYears")
    public List<Integer> getExpiryYears() {
        return creditCardRepository.getExpiryYears();
    }

    @GetMapping("partitions")
    public Map<Integer, List<CreditCard>> getPartitions() {
        return creditCardRepository.getPartitions();
    }

}
