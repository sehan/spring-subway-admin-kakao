package subway.core;

import org.springframework.util.Assert;

import java.util.*;

public class Sections {

    private Set<Station> stations = new HashSet<>();
    private final List<Section> sections = new ArrayList<>();

    public Sections(Section section) {
        Assert.notNull(section, "최소 1개의 섹션이 필요합니다");
        this.sections.add(section);
        this.stations.add(section.getUpStation());
        this.stations.add(section.getDownStation());
    }

    public boolean hasStation(Station station) {
        return stations.contains(station);
    }

    public void addSection(Station upStation, Station downStation, long distance) {

        List<Station> intersections = findIntersection(upStation, downStation);

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
            sections.add(Section.of(upStation, downStation, distance));
            stations.add(upStation);
            return;
        }

        if( nextSection == null ){
            // 연결역이 하행종점
            sections.add(Section.of(upStation, downStation, distance));
            stations.add(downStation);
            return;
        }

        if( intersection.equals(downStation) ){
            prevSection.changeDownStation(upStation, distance);
            sections.add(Section.of(upStation, downStation, distance));
            stations.add(upStation);
        }

        if( intersection.equals(upStation)){
            nextSection.changeUpStation(downStation, distance);
            sections.add(Section.of(upStation, downStation, distance));
            stations.add(downStation);
        }

    }

    private List<Station> findIntersection(Station up, Station down) {
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

}
