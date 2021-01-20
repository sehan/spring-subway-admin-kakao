package subway.line.jdbc;

import subway.line.model.Line;
import subway.line.model.Section;
import subway.station.Station;

public class SectionEntity {

    private Long id;
    private Long lineId;
    private Long upStationId;
    private Long downStationId;
    private Long distance;

    public SectionEntity(){};

    SectionEntity(long id, long lineId, long upStationId, long downStationId, long distance) {
        this.id = id;
        this.lineId = lineId;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    private SectionEntity(long lineId, long upStationId, long downStationId, long distance) {
        this.lineId = lineId;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public long getId() {
        return id;
    }

    public Long getLineId() {
        return lineId;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public Long getDistance() {
        return distance;
    }

    public static SectionEntity of(Line line, Section section) {
        return new SectionEntity(
                line.getId(),
                section.getUpStation().getId(),
                section.getDownStation().getId(),
                section.getDistance()
        );
    }

    public Section toModel() {
        return Section.of(
                id,
                Station.ref(upStationId),
                Station.ref(downStationId),
                distance
        );
    }
}
