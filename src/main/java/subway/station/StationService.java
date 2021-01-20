package subway.station;

import java.util.List;

public interface StationService {

    Station get(Long id);

    void delete(Long id);

    Station create(String name);

    List<Station> getAll();
}






