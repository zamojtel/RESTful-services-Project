package pl.zamojtel.project.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class RateDto {
    private String name;
    private double value;
}
