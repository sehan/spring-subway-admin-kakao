package subway.web.station;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.core.Station;
import subway.core.StationRegistry;

import java.net.URI;
import java.util.List;

@RestController
public class StationController {

    private StationRegistry stationRegistry;

    public StationController(StationRegistry stationRegistry) {
        this.stationRegistry = stationRegistry;
    }

    @PostMapping("/stations")
    public ResponseEntity<StationResponse> createStation(@RequestBody StationRequest stationRequest) {
        Station newStation = stationRegistry.add(stationRequest.getName());
        StationResponse stationResponse = new StationResponse(newStation.getId(), newStation.getName());
        return ResponseEntity.created(URI.create("/stations/" + newStation.getId())).body(stationResponse);
    }

    @GetMapping(value = "/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StationResponse>> showStations() {
        List<Station> stations = stationRegistry.getAll();
        return ResponseEntity.ok().body(StationResponses.convertFrom(stations));
    }

    @GetMapping(value = "/stations/{id}")
    public ResponseEntity<StationResponse> get(@PathVariable Long id) {
        try {
            Station station = stationRegistry.findOne(id);
            return ResponseEntity.ok(StationResponse.of(station));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/stations/{id}")
    public ResponseEntity deleteStation(@PathVariable Long id) {
        stationRegistry.remove(id);
        return ResponseEntity.noContent().build();
    }

}
