package subway.web.station;

import subway.core.Station;

import java.util.List;
import java.util.stream.Collectors;

public class StationResponses {

    public static List<StationResponse> convertFrom(List<Station> stations) {
        return stations.stream()
                .map(station -> new StationResponse(station.getId(), station.getName()))
                .collect(Collectors.toList());
    }

}
