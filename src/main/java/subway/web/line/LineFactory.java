package subway.web.line;

import subway.core.Line;

public class LineFactory {
    public static Line create(LineRequest createRequest) {
        return new Line(createRequest.getName(), createRequest.getColor());
    }

    public static Line create(Long id, LineRequest lineRequest) {
        return Line.of(id, lineRequest.getName(), lineRequest.getColor());
    }
}
