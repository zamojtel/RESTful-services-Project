package pl.zamojtel.project.web.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.zamojtel.project.domain.models.ApiEnum;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatesDto {
    double minRate;
    double maxRate;
    double avgRate;

    List<RateDto> rates;
}
