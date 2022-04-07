package pl.zamojtel.project.web.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class SimpleResponseView<U> {
    private U data;
}
