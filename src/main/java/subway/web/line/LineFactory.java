package subway.web.line;

import subway.core.Line;

public class LineFactory {
    public static Line create(LineRequest createRequest) {
        return new Line(createRequest.getName(), createRequest.getColor());
    }
}
