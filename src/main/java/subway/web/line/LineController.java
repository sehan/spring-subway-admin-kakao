package subway.web.line;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.core.Line;
import subway.core.LineManager;
import subway.core.Station;
import subway.core.StationRegistry;

import java.util.List;

@RestController
@RequestMapping("/lines")
public class LineController {

    private final LineManager lineManager;
    private final StationRegistry stationRegistry;

    public LineController(LineManager lineManager, StationRegistry stationRegistry) {
        this.lineManager = lineManager;
        this.stationRegistry = stationRegistry;
    }

    @PostMapping
    public ResponseEntity<LineResponse> create(@RequestBody LineRequest createRequest) {
        Line line = lineManager.create(
                LineFactory.create(createRequest),
                SectionFactory.create(createRequest)
        );
        return ResponseEntity.ok(LineResponse.from(line));
    }

    @GetMapping
    public ResponseEntity<List<LineResponse>> getAll() {
        List<Line> lines = lineManager.getAll();
        return ResponseEntity.ok(LineResponses.convertFrom(lines));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        lineManager.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LineResponse> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(LineResponse.from(lineManager.findOne(id)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/{id}/sections")
    public ResponseEntity<LineResponse> createSection(@PathVariable Long id, @RequestBody SectionRequest sectionRequest){
        lineManager.addSection(id,
                stationRegistry.findOne(sectionRequest.getUpStationId()),
                stationRegistry.findOne(sectionRequest.getDownStationId()),
                sectionRequest.getDistance());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}/sections")
    public ResponseEntity<LineResponse> deleteSection(@PathVariable Long id, long stationId){
        lineManager.removeSection(id, Station.ref(stationId));
        return ResponseEntity.ok().build();
    }

}
