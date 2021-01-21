package subway.web.line;

import subway.core.Section;

public class SectionFactory {
    public static Section create(LineRequest createRequest) {
        return new Section(
                createRequest.getUpStationId(),
                createRequest.getDownStationId(),
                createRequest.getDistance());
    }

    public static Section create(SectionRequest sectionRequest){
        return new Section(
                sectionRequest.getUpStationId(),
                sectionRequest.getDownStationId(),
                sectionRequest.getDistance()
        );
    }
}
