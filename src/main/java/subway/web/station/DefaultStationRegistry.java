package subway.web.station;

import org.springframework.stereotype.Service;
import subway.core.Station;
import subway.core.StationRegistry;

import java.util.List;

public class DefaultStationRegistry implements StationRegistry {

    private StationDao stationDao;

    public DefaultStationRegistry(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    @Override
    public Station findOne(Long stationId) {
        Station station = stationDao.findById(stationId)
                .orElseThrow(() -> new NotFoundStationException(stationId));
        return station;
    }

    @Override
    public void remove(Long stationId) {
        stationDao.deleteById(stationId);
    }

    @Override
    public Station add(String name) {
        return stationDao.save(Station.of(name));
    }

    @Override
    public List<Station> getAll() {
        return stationDao.findAll();
    }
}
