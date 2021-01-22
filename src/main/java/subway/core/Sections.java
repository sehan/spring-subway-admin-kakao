package subway.core;

import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sections {

    private Set<Station> stations = new HashSet<>();
    private List<Section> sections = new ArrayList<>();

    private Sections(){}

    public Sections(Section section) {
        Assert.notNull(section, "최소 1개의 섹션이 필요합니다");
        this.sections.add(section);
        this.stations.add(section.getUpStation());
        this.stations.add(section.getDownStation());
    }

    private void setSections(List<Section> sections){
        this.sections = sections;
        this.stations = sections.stream()
                .flatMap(section -> Stream.of(section.getUpStation(), section.getDownStation()))
                .collect(Collectors.toSet());
    }

    public static Sections loadFrom(List<Section> sectionData){
        Sections sections = new Sections();
        sections.setSections(sectionData);
        return sections;
    }

    public boolean hasStation(Station station) {
        return stations.contains(station);
    }

    public void addSection(Station upStation, Station downStation, long distance){
        addSection(Section.of(upStation, downStation, distance));
    }

    public void addSection(Section section) {
        Station upStation = section.getUpStation();
        Station downStation = section.getDownStation();
        long distance = section.getDistance();

        List<Station> intersections = findIntersectionStations(upStation, downStation);

        if (intersections.size() == 2) {
            throw new IllegalArgumentException("이미 존재하는 구간입니다");
        }
        if (intersections.size() == 0 ) {
            throw new IllegalArgumentException("현재 라인에 연결할 수 없는 구간입니다");
        }

        Station intersection = intersections.get(0);
        Section prevSection = getUpSectionOf(intersection);
        Section nextSection = getDownSectionOf(intersection);

        if( prevSection == null ){
            // 연결역이 상행종점
            sections.add(section);
            stations.add(section.getUpStation());
            return;
        }

        if( nextSection == null ){
            // 연결역이 하행종점
            sections.add(section);
            stations.add(downStation);
            return;
        }

        if( intersection.equals(downStation) ){
            prevSection.changeDownStation(upStation, distance);
            sections.add(section);
            stations.add(upStation);
        }

        if( intersection.equals(upStation)){
            nextSection.changeUpStation(downStation, distance);
            sections.add(section);
            stations.add(downStation);
        }

    }

    private List<Station> findIntersectionStations(Station up, Station down) {
        Set clone = new HashSet(stations);
        clone.retainAll(Arrays.asList(up,down));
        return new ArrayList<>(clone);
    }

    public Section findByDownStation(Station down) {
        return sections.stream()
                .filter(section -> section.getDownStation().equals(down))
                .findFirst()
                .orElse(null);
    }

    public Section findByUpStation(Station up) {
        return sections.stream()
                .filter(section -> section.getUpStation().equals(up))
                .findFirst()
                .orElse(null);
    }

    public Section getDownSectionOf(Station station) {
        return sections.stream()
                .filter(section -> section.getUpStation().equals(station))
                .findFirst()
                .orElse(null);
    }

    public Section getUpSectionOf(Station station) {
        return sections.stream()
                .filter(section -> section.getDownStation().equals(station))
                .findFirst()
                .orElse(null);
    }

    public List<Station> getStations() {
        return Collections.unmodifiableList(new ArrayList(stations));
    }

    public List<Section> toList() {
        return Collections.unmodifiableList(sections);
    }
}
