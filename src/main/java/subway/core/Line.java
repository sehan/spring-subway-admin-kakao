package subway.core;

import java.util.List;

public class Line {

    private Long id;
    private String name;
    private String color;

    @Deprecated
    private Stations stations;
    private Sections sections;

    private Line(Long id, String name, String color, Section section) {
        this(id, name, color, new Sections(section));
    }

    private Line(Long id, String name, String color, Sections sections) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.sections = sections;
    }


    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    private Line(long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public static Line of(Long id, String name, String color) {
        return new Line(id, name, color);
    }

    public static Line of(Long id, String name, String color, Section section) {
        return new Line(id, name, color, section);
    }

    private static Line of(Long id, String name, String color, Sections sections) {
        return new Line(id, name, color, sections);
    }

    public static Builder builder() {
        return new Builder();
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

    public List<Station> getStations(){
        return sections.getStations();
    }

    public void addSection(Section section){
        sections.addSection(section);
    }

    public void addSection(Station upStation, Station downStation, long distance) {
        addSection(Section.of(upStation, downStation, distance));
    }

    public List<Section> getSections() {
        return sections.toList();
    }

    public static class Builder {

        private String name;
        private String color;
        private Long lineId;
        private List<Section> sections;

        public Builder setId(Long lineId) {
            this.lineId = lineId;
            return this;
        }

        public Builder nameAndColor(String name, String color) {
            this.name = name;
            this.color = color;
            return this;
        }

        public Builder sections(List<Section> sections) {
            this.sections = sections;
            return this;
        }

        public Line build() {
            return Line.of(lineId, name, color, Sections.loadFrom(sections));
        }

        public Builder lineId(Long lineId) {
            this.lineId = lineId;
            return this;
        }
    }
}
