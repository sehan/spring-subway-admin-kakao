package subway.core;

import java.util.List;

public class Stations {

    private List<Station> stations;

    public Stations(List<Station> stations) {
        this.stations = stations;
    }

    public boolean contains(Station station) {
        return stations.contains(station);
    }

    public Station getFirstStation() {
        return null;
    }

    public Station getLastStation() {
        return null;
    }
}
