package subway.web.station.jdbc;

import org.springframework.stereotype.Service;
import subway.core.Station;
import subway.core.StationRegistry;

import java.util.List;

@Service
public class JdbcStationRegistry implements StationRegistry {

    private StationTemplate template;

    public JdbcStationRegistry(StationTemplate template) {
        this.template = template;
    }

    @Override
    public Station findOne(Long stationId) {
        return null;
    }

    @Override
    public void remove(Long stationId) {

    }

    @Override
    public Station add(String name) {
        return template.save(Station.of(name));
    }

    @Override
    public List<Station> getAll() {
        return null;
    }
}
