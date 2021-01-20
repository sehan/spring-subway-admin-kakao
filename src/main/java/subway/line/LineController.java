package subway.line;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subway.line.model.Line;
import subway.line.model.LineService;

import java.util.List;

@RestController
@RequestMapping("/lines")
public class LineController {

    private LineService lineService;

    @PostMapping
    public ResponseEntity<LineResponse> create(@RequestBody LineRequest createRequest) {
        Line line = lineService.create(
                LineFactory.create(createRequest),
                SectionFactory.create(createRequest)
        );
        return ResponseEntity.ok(LineResponse.from(line));
    }

    @GetMapping
    public ResponseEntity<List<LineResponse>> getAll() {
        List<Line> lines = lineService.getAll();
        return ResponseEntity.ok(LineResponses.convertFrom(lines));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        lineService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LineResponse> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(LineResponse.from(lineService.get(id)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
