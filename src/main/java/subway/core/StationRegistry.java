package subway.core;

import java.util.List;

public interface StationRegistry {

    Station findOne(Long stationId);

    void remove(Long stationId);

    Station add(String name);

    List<Station> getAll();

//    Station edit(Station station);
//
//
//    Station find(String name);

}






