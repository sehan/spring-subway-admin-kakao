package subway.station.jdbc;

import org.springframework.stereotype.Service;
import subway.station.Station;
import subway.station.StationService;

import java.util.List;

@Service
public class JdbcStationService implements StationService {

    private StationTemplate template;

    public JdbcStationService(StationTemplate template) {
        this.template = template;
    }

    @Override
    public Station get(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Station create(String name) {
        return template.save(Station.of(name));
    }

    @Override
    public List<Station> getAll() {
        return null;
    }
}
