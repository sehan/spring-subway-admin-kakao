package subway.line;

import subway.line.model.Section;

public class SectionFactory {
    public static Section create(LineRequest createRequest) {
        return new Section(
                createRequest.getUpStationId(),
                createRequest.getDownStationId(),
                createRequest.getDistance());
    }
}
