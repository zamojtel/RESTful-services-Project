package pl.zamojtel.project.web.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class SimpleResponseListView<U> {
    private long size;
    private List<U> data;
}
