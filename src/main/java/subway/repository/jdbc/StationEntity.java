package subway.repository.jdbc;

import subway.core.Station;

public class StationEntity {

    private Long id;
    private String name;

    public StationEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Station toModel() {
        return new Station(id, name);
    }
}
