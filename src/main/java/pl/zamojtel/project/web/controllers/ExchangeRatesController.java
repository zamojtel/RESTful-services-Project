package pl.zamojtel.project.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import pl.zamojtel.project.domain.services.ExchangesService;
import pl.zamojtel.project.web.dto.RatesDto;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("/exchange")
public class ExchangeRatesController {

    private final ExchangesService exchangesService;

    public ExchangeRatesController(ExchangesService exchangesService) {
        this.exchangesService = exchangesService;
    }

    @GetMapping(value = "/rates")
    public ResponseEntity<RatesDto> getRatesFromApis(@RequestParam(name = "date", defaultValue = "") String date,
            @RequestParam(name = "currency", defaultValue = "pln") String currency) {

        return ResponseEntity.ok(exchangesService.getRates(date, currency));
    }

    @GetMapping(value = "/test")
    public ResponseEntity<String> getRatesFromApiTest(@RequestParam(name = "date", defaultValue = "") String date,
            @RequestParam(name = "currency", defaultValue = "pln") String currency) {
        log.info("getRatesFromApiTest({}, {})", date, currency);
        return ResponseEntity.ok(
                "{\"minRate\":4.26697,\"maxRate\":4.2707,\"avgRate\":4.268213333333333,\"rates\":[{\"name\":\"OPEN_RATES\",\"value\":4.26697},{\"name\":\"NBP\",\"value\":4.2707},{\"name\":\"CURRENCYLAYER\",\"value\":4.26697}]}");
    }

    @GetMapping(value = "/error")
    public ResponseEntity<String> getError(@RequestParam(name = "date", defaultValue = "") String date,
            @RequestParam(name = "currency", defaultValue = "pln") String currency) {
        log.info("getRatesFromApiTest({}, {})", date, currency);
        throw new NullPointerException("asd");
    }

}
