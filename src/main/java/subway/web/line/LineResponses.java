package subway.web.line;

import subway.core.Line;

import java.util.List;
import java.util.stream.Collectors;

public class LineResponses {
    public static List<LineResponse> convertFrom(List<Line> lines) {
        return lines.stream()
                .map(LineResponse::from)
                .collect(Collectors.toList());
    }
}
