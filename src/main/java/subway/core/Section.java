package subway.core;

import java.util.Objects;

public class Section {

    private Long id;
    private Station upStation;
    private Station downStation;
    private long distance;

    public Section(long upStationId, long downStationId, long distance) {
        this(Station.ref(upStationId), Station.ref(downStationId), distance);
    }

    public Section(Station upStation, Station downStation, long distance) {
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    private Section(long id, Station upStation, Station downStation, long distance) {
        this.id = id;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public static Section of(long upStationId, long downStationId, long distance) {
        return new Section(upStationId, downStationId, distance);
    }

    public static Section of(Station up, Station down, long distance) {
        return new Section(up, down, distance);
    }

    public static Section of(long id, Station upStation, Station downStation, long distance) {
        return new Section(id, upStation, downStation, distance);
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public long getId() {
        return id;
    }

    public long getDistance() {
        return distance;
    }

    public boolean isLonger(long thanDistance) {
        return distance > thanDistance;
    }

    public void changeDownStation(Station newDownStation, long distanceOfNewSection) {
        if (distance <= distanceOfNewSection) throw new IllegalArgumentException("종점이 아닌 새로운 구간의 길이가 기존 구간보다 길면 안됩니다");
        downStation = newDownStation;
        distance -= distanceOfNewSection;
    }

    public void changeUpStation(Station newUpStation, long distanceOfNewSection) {
        if (distance <= distanceOfNewSection) throw new IllegalArgumentException("종점이 아닌 새로운 구간의 길이가 기존 구간보다 길면 안됩니다");
        upStation = newUpStation;
        distance -= distanceOfNewSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return distance == section.distance && Objects.equals(upStation, section.upStation) && Objects.equals(downStation, section.downStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(upStation, downStation, distance);
    }

    @Override
    public String toString() {
        return "Section{" +
                "upStation=" + upStation +
                ", downStation=" + downStation +
                ", distance=" + distance +
                '}';
    }

    public boolean hasId() {
        return Objects.nonNull(id);
    }

    public Section merge(Section target) {
        downStation = target.getDownStation();
        distance += target.getDistance();
        return this;
    }
}
