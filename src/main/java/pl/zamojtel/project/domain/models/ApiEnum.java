package pl.zamojtel.project.domain.models;

import lombok.Getter;


@Getter
public enum ApiEnum {
    AMDOREN("AMDOREN"),
    OPEN_RATES("OPEN_RATES"),
    NBP("NBP"),
    CURRENCYLAYER("CURRENCYLAYER");

    private final String api;

    ApiEnum(String api) {
        this.api = api;
    }
}
