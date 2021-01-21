package subway.core;

import java.util.List;

public class Line {

    private Long id;
    private String name;
    private String color;

    @Deprecated
    private Stations stations;
    private Sections sections;

    private Line(Long id, String name, String color){
        this(id, name,color, null);
    }

    private Line(Long id, String name, String color, Section section){
        this.id = id;
        this.name = name;
        this.color = color;
        this.sections = new Sections(section);
    }

    public Line(String name, String color) {
        this(null, name, color, null);
    }

    public static Line of(Long id, String name, String color){
        return new Line(id, name, color, null);
    }

    public static Line of(Long id, String name, String color, Section section){
        return new Line(id, name, color, section);
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

    public boolean hasStation(Station station) {
        return sections.hasStation(station);
    }

    public void addSection(Station upStation, Station downStation, long distance) {
        sections.addSection(upStation, downStation, distance);
    }

    public List<Station> getStations() {
        return null;
    }
}
