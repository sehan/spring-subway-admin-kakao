package subway.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Stations {

    private LinkedList<Station> stations = new LinkedList<>();

    Stations() {
    }

    public Stations(List<Section> sections) {
        for (Section section : sections) {
            add(section.getUpStation(), section.getDownStation());
        }
    }

    public boolean contains(Station station) {
        return stations.contains(station);
    }

    public void add(Station upStation, Station downStation) {
        if (stations.size() == 0) {
            stations.add(upStation);
            stations.add(downStation);
            return;
        }

        if (contains(upStation)) {
            try {
                stations.add(stations.indexOf(upStation) + 1, downStation);
            } catch (IndexOutOfBoundsException e) {
                stations.add(downStation);
            }
            return;
        }

        if (contains(downStation)) {
            try {
                stations.add(stations.indexOf(downStation), upStation);
            } catch (IndexOutOfBoundsException e) {
                stations.addFirst(upStation);
            }
            return;
        }
    }

    public List<Station> toList() {
        return Collections.unmodifiableList(stations);
    }

    public void remove(Station station) {
        stations.remove(station);
    }
}
