package subway.line.model;

import subway.station.Station;

import java.util.List;

public class Line {

    private Long id;
    private String name;
    private String color;

    private List<Station> stations;

    private Line(Long id, String name, String color){
        this(id, name,color, null);
    }

    private Line(Long id, String name, String color, List<Station> stations){
        this.id = id;
        this.name = name;
        this.color = color;
        this.stations = stations;
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public static Line of(Long id, String name, String color, List<Station> stations){
        return new Line(id, name, color, stations);
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Long getId() {
        return id;
    }

    public List<Station> getStations() {
        return stations;
    }
}
