package subway.line;

import subway.line.model.Line;

public class LineFactory {
    public static Line create(LineRequest createRequest) {
        return new Line(createRequest.getName(), createRequest.getColor());
    }
}
