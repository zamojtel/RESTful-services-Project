package pl.zamojtel.project.domain.services;

import pl.zamojtel.project.web.dto.RatesDto;


public interface ExchangesService {
    RatesDto getRates(String date, String currency);
}
