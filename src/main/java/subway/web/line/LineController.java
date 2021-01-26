package subway.web.line;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.core.Line;
import subway.core.LineManager;
import subway.core.StationRegistry;

import java.net.URI;
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
                stationRegistry.findOne(createRequest.getUpStationId()),
                stationRegistry.findOne(createRequest.getDownStationId()),
                createRequest.getDistance()
        );
        return ResponseEntity.created(URI.create("/lines/" + line.getId())).body(LineResponse.from(line));
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
        return ResponseEntity.ok(LineResponse.from(lineManager.findOne(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<LineResponse> update(@PathVariable Long id, @RequestBody LineRequest lineRequest) {
        return ResponseEntity.ok(LineResponse.from(lineManager.update(LineFactory.create(id, lineRequest))));

    }

    @PostMapping(value = "/{id}/sections")
    public ResponseEntity<LineResponse> createSection(@PathVariable Long id, @RequestBody SectionRequest sectionRequest) {
        lineManager.addSection(id,
                stationRegistry.findOne(sectionRequest.getUpStationId()),
                stationRegistry.findOne(sectionRequest.getDownStationId()),
                sectionRequest.getDistance());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}/sections")
    public ResponseEntity<LineResponse> deleteSection(@PathVariable Long id, long stationId) {
        lineManager.removeSection(id, stationRegistry.findOne(stationId));
        return ResponseEntity.ok().build();
    }

}
