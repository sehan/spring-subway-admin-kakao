package subway.station;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class StationController {

    private StationService stationService;

    public StationController(StationService stationService){
        this.stationService = stationService;
    }

    @PostMapping("/stations")
    public ResponseEntity<StationResponse> createStation(@RequestBody StationRequest stationRequest) {
        Station newStation = stationService.create(stationRequest.getName());
        StationResponse stationResponse = new StationResponse(newStation.getId(), newStation.getName());
        return ResponseEntity.created(URI.create("/stations/" + newStation.getId())).body(stationResponse);
    }

    @GetMapping(value = "/stations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StationResponse>> showStations() {
        List<Station> stations = stationService.getAll();
        return ResponseEntity.ok().body(StationResponses.convertFrom(stations));
    }

    @GetMapping(value ="/stations/{id}")
    public ResponseEntity<StationResponse> get(@PathVariable Long id){
        try {
            Station station = stationService.get(id);
            return ResponseEntity.ok(StationResponse.of(station));
        } catch ( IllegalArgumentException e ){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/stations/{id}")
    public ResponseEntity deleteStation(@PathVariable Long id) {
        stationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
