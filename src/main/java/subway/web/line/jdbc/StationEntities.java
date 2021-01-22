package subway.web.line.jdbc;

import subway.web.station.StationEntity;

import java.util.List;

public class StationEntities {

    private final List<StationEntity> entities;

    public StationEntities(List<StationEntity> entities) {
        this.entities = entities;
    }

    public StationEntity get(Long stationId) {
        return entities.stream()
                .filter(entity -> entity.getId() == stationId)
                .findFirst()
                .orElse(null);
    }
}
