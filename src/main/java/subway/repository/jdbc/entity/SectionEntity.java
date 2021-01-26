package subway.repository.jdbc.entity;

import subway.core.Section;
import subway.core.Station;

import java.util.Objects;

public class SectionEntity {

    private Long id;
    private Long lineId;
    private Long upStationId;
    private Long downStationId;
    private Long distance;

    private SectionEntity() {
    }

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

    public boolean hasId() {
        return Objects.nonNull(id);
    }

    public static SectionEntity transientInstance(long lineId, Section section) {
        return new SectionEntity(
                lineId,
                section.getUpStation().getId(),
                section.getDownStation().getId(),
                section.getDistance()
        );
    }

    public static SectionEntity persistInstance(long lineId, Section section) {
        return new SectionEntity(
                section.getId(),
                lineId,
                section.getUpStation().getId(),
                section.getDownStation().getId(),
                section.getDistance()
        );
    }

    public static SectionEntity of(long lineId, Section section) {
        if (section.hasId())
            return persistInstance(lineId, section);
        return transientInstance(lineId, section);
    }

    public Section toModel() {
        return Section.of(
                id,
                Station.ref(upStationId),
                Station.ref(downStationId),
                distance
        );
    }

    public Section toModel(StationEntity upStation, StationEntity downStation) {
        return Section.of(
                id,
                upStation.toModel(),
                downStation.toModel(),
                distance
        );
    }
}
