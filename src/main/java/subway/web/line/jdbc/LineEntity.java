package subway.web.line.jdbc;


import subway.core.Line;
import subway.web.station.StationEntity;

import java.util.List;
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

    LineEntity(String name, String color){
        this.name = name;
        this.color = color;
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

    public static LineEntity transientInstance(Line line){
        return new LineEntity(line.getName(), line.getColor());
    }

    public static LineEntity persistInstance(long id, String name, String color) {
        return new LineEntity(id, name, color);
    }

    public Line toModel(List<StationEntity> stations) {
//        stations.stream().map(station -> station.toModel()).collect(Collectors.toList())
        return Line.of(
                id,
                name,
                color,
                null);
    }

    public Line toModel() {
        return Line.of(
                id,
                name,
                color,
                null);
    }
}
