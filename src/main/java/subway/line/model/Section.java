package subway.line.model;

import subway.station.Station;

public class Section {

    private long id;
    private Station upStation;
    private Station downStation;
    private long distance;

    public Section(long upStationId, long downStationId, long distance) {
        this.upStation = Station.ref(upStationId);
        this.downStation = Station.ref(downStationId);
        this.distance = distance;
    }

    private Section(long id, Station upStation, Station downStation, long distance){
        this.id = id;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public static Section of(long upStationId, long downStationId, int distance){
        return new Section(upStationId, downStationId, distance);
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

    public long getDistance() {
        return distance;
    }
}
