package subway.core;

import org.springframework.util.Assert;

import java.util.*;

public class Sections implements SectionEventSupport {

    private Stations stations;
    private List<Section> sections = new ArrayList<>();

    private SectionEventListener sectionEventListener;

    private Sections() {
    }

    public Sections(Section section) {
        Assert.notNull(section, "최소 1개의 섹션이 필요합니다");
        this.sections.add(section);
        this.stations = new Stations(Arrays.asList(section));
    }

    private void setSections(List<Section> sections) {
        this.sections = sections;
        this.stations = new Stations(sections);
    }

    /**
     * sectionData 는 section 이 생성된 순서대로 정렬된 Data
     *
     * @param sectionData
     * @return
     */
    public static Sections loadFrom(List<Section> sectionData) {
        Sections sections = new Sections();
        sections.setSections(sectionData);
        return sections;
    }

    public boolean hasStation(Station station) {
        return stations.contains(station);
    }

    public Station getNextStation(Station station) {
        Section down = getDownSectionOf(station);
        if (Objects.nonNull(down)) {
            return down.getDownStation();
        }
        return null;
    }

    public void addSection(Station upStation, Station downStation, long distance) {
        addSection(Section.of(upStation, downStation, distance));
    }

    public void addSection(Section section) {
        Station upStation = section.getUpStation();
        Station downStation = section.getDownStation();
        long distance = section.getDistance();

        List<Station> intersections = findIntersectionStations(upStation, downStation);

        if (intersections.size() == 2) throw new AlreadyExistSectionException("이미 존재하는 구간입니다");
        if (intersections.size() == 0) {
            throw new IllegalSectionException("현재 라인에 연결할 수 없는 구간입니다");
        }

        Station intersection = intersections.get(0);
        Section prevSection = getUpSectionOf(intersection);
        Section nextSection = getDownSectionOf(intersection);

        if (prevSection == null) {
            // 연결역이 상행종점
            sections.add(section);
            stations.add(upStation, downStation);
            return;
        }

        if (nextSection == null) {
            // 연결역이 하행종점
            sections.add(section);
            stations.add(upStation, downStation);
            return;
        }

        if (intersection.equals(downStation)) {
            prevSection.changeDownStation(upStation, distance);
            sections.add(section);
            stations.add(upStation, downStation);
        }

        if (intersection.equals(upStation)) {
            nextSection.changeUpStation(downStation, distance);
            sections.add(section);
            stations.add(upStation, downStation);
        }

    }

    private List<Station> findIntersectionStations(Station... targetStations) {
        Set sourceGroup = new HashSet(stations.toList());
        Set targetGroup = new HashSet(Arrays.asList(targetStations));
        sourceGroup.retainAll(targetGroup);
        return new ArrayList<>(sourceGroup);
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
        return Collections.unmodifiableList(stations.toList());
    }

    public List<Section> toList() {
        return Collections.unmodifiableList(sections);
    }

    public void removeSection(Station station) {
        if (sections.size() == 1) {
            throw new IllegalStateException("노선이 하나인 경우 삭제 할 수 없습니다");
        }
        if (!hasStation(station)) {
            throw new IllegalArgumentException("노선에 존재하지 않는 역입니다");
        }

        Section prevSection = getUpSectionOf(station);
        Section nextSection = getDownSectionOf(station);

        List<SectionEvent> events = new ArrayList<>();
        if (prevSection == null) {
            // 삭제역이 상행종점
            sections.remove(nextSection);
            stations.remove(station);
            events.add(SectionEvent.of(nextSection, SectionEvent.Type.DELETE));
            notifySectionEvents(events);
            return;
        }

        if (nextSection == null) {
            // 삭제역이 하행종점
            sections.remove(prevSection);
            stations.remove(station);
            events.add(SectionEvent.of(prevSection, SectionEvent.Type.DELETE));
            notifySectionEvents(events);
            return;
        }

        prevSection.merge(nextSection);
        sections.remove(nextSection);
        stations.remove(station);
        events.add(SectionEvent.of(prevSection, SectionEvent.Type.UPDATE));
        events.add(SectionEvent.of(nextSection, SectionEvent.Type.DELETE));
        notifySectionEvents(events);
    }

    @Override
    public void onChangedSection(SectionEventListener listener) {
        sectionEventListener = listener;
    }

    @Override
    public void notifySectionEvents(List<SectionEvent> events) {
        if (Objects.nonNull(sectionEventListener)) {
            sectionEventListener.handleEvent(events);
        }
    }
}
