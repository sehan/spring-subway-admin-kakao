package subway.station;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultStationService implements StationService{

    private StationDao stationDao;

    public DefaultStationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    @Override
    public Station get(Long id) {
        Station station = stationDao.findById(id)
                .orElseThrow(() -> new NotFoundStationException(id));
        return station;
    }

    @Override
    public void delete(Long id) {
        stationDao.deleteById(id);
    }

    @Override
    public Station create(String name) {
        return stationDao.save(Station.of(name));
    }

    @Override
    public List<Station> getAll() {
        return stationDao.findAll();
    }
}
