package subway.station;

import java.util.Objects;

public class Station {
    private Long id;
    private String name;

    public Station() {
    }

    private Station(Long id){
        this.id = id;
    }

    public Station(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Station(String name) {
        this.name = name;
    }

    public static Station ref(Long id) { return new Station(id); }
    public static Station of(String name) {
        return new Station(name);
    }
    public static Station of(Long id, String name) { return new Station(id, name); }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(id, station.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

