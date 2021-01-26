package subway.repository.jdbc.entity;


import subway.core.Line;
import subway.core.Section;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LineEntity {

    private Long id;
    private String name;
    private String color;

    LineEntity(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    LineEntity(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public static LineEntity of(Line line) {
        if (Objects.nonNull(line.getId())) {
            return persistInstance(line.getId(), line.getName(), line.getColor());
        }
        return transientInstance(line);
    }

    public static LineEntity transientInstance(Line line) {
        return new LineEntity(line.getName(), line.getColor());
    }

    public static LineEntity persistInstance(long id, String name, String color) {
        return new LineEntity(id, name, color);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Line toModel(List<SectionEntity> sectionEntities, List<StationEntity> stationEntities) {
        StationEntities stations = new StationEntities(stationEntities);
        List<Section> sections = sectionEntities.stream()
                .map(sectionEntity -> sectionEntity.toModel(
                        stations.get(sectionEntity.getUpStationId()),
                        stations.get(sectionEntity.getDownStationId())
                ))
                .collect(Collectors.toList());

        return Line.builder()
                .lineId(id)
                .nameAndColor(name, color)
                .sections(sections)
                .build();
    }

    public Line toModel() {
        return Line.of(
                id,
                name,
                color);
    }

    public boolean hasId() {
        return Objects.nonNull(id);
    }
}
