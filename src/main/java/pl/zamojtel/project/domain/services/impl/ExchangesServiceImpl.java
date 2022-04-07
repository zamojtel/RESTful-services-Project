package pl.zamojtel.project.domain.services.impl;

import static java.util.Objects.isNull;
import static pl.zamojtel.project.domain.utils.ApiUtils.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import pl.zamojtel.project.domain.models.ApiEnum;
import pl.zamojtel.project.domain.services.ExchangesService;
import pl.zamojtel.project.web.dto.RateDto;
import pl.zamojtel.project.web.dto.RatesDto;


@Service
public class ExchangesServiceImpl implements ExchangesService {

    @Override
    public RatesDto getRates(String date, String currency) {
        EnumMap<ApiEnum, Double> rates = new EnumMap<>(ApiEnum.class);

        getRatesFromAllApis(date, currency, rates);
        removeBadValuesFromRates(rates);
        RatesDto ratesDto = new RatesDto();
        ratesDto.setMinRate(computeMinimalRate(rates));
        ratesDto.setMaxRate(computeMaximumRate(rates));
        ratesDto.setAvgRate(computeAverageRate(rates));

        List<RateDto> rateDtoList = new ArrayList<>();
        rates.keySet()
                .forEach(key -> rateDtoList.add(RateDto.builder().name(key.getApi()).value(rates.get(key)).build()));
        ratesDto.setRates(rateDtoList);

        return ratesDto;
    }

    private Double computeMinimalRate(Map<ApiEnum, Double> rates) {
        return Collections.min(rates.values());
    }

    private Double computeMaximumRate(Map<ApiEnum, Double> rates) {
        return Collections.max(rates.values());
    }

    private Double computeAverageRate(Map<ApiEnum, Double> rates) {
        return rates.values().stream().mapToDouble(Double::doubleValue).sum() / rates.values().size();
    }

    private void removeBadValuesFromRates(EnumMap<ApiEnum, Double> rates) {
        Arrays.stream(ApiEnum.values()).forEach(api -> {
            if (!isNull(rates.get(api)) && rates.get(api) == 0) {
                rates.remove(api);
            }

        });
    }

    private void getRatesFromAllApis(String date, String currency, Map<ApiEnum, Double> rates) {
        Arrays.stream(ApiEnum.values()).forEach(api -> {
            try {
                JsonElement jsonElement = getResponseFromUrl(date, currency, api);
                if (!isNull(jsonElement) && !jsonElement.isJsonNull()) {
                    Double value = getValueFromJsonElement(jsonElement, api);
                    if (!isNull(value)) {
                        rates.put(api, value);
                    }
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        });
    }

    private Double getValueFromJsonElement(JsonElement jsonElement, ApiEnum api) {
        JsonObject root = jsonElement.getAsJsonObject();
        String value = "0";
        switch (api) {
        case NBP -> {
            root = root.getAsJsonArray(RATES).get(0).getAsJsonObject();
            value = root.get(MID).getAsString();
        }
        case CURRENCYLAYER -> {
            JsonObject jsonObject = root.getAsJsonObject();
            if (isNull(jsonObject)) {
                return null;
            }
            jsonObject = jsonObject.get(QUOTES).getAsJsonObject();
            if (isNull(jsonObject)) {
                return null;
            }
            JsonElement element = jsonObject.get(USDPLN);
            if (isNull(element) || element.isJsonNull()) {
                return null;
            }
            value = element.getAsString();
        }
        case OPEN_RATES -> {
            JsonObject jsonObject = root.getAsJsonObject();
            if (isNull(jsonObject)) {
                return null;
            }
            jsonObject = jsonObject.get(RATES).getAsJsonObject();
            if (isNull(jsonObject)) {
                return null;
            }
            JsonElement element = jsonObject.get(PLN.toUpperCase());
            if (isNull(element) || element.isJsonNull()) {
                return null;
            }
            value = element.getAsString();
        }
        case AMDOREN -> {
            value = root.get(AMOUNT).getAsString();
        }
        }
        return Double.parseDouble(value);
    }
}
