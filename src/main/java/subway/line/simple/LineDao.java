package subway.line.simple;

import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;
import subway.line.model.Line;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LineDao {

    private Long seq = 0L;
    private List<Line> lines = new ArrayList<>();

    public Line save(Line line) {
        Line persistLine = createNewObject(line);
        lines.add(persistLine);
        return persistLine;
    }

    private Line createNewObject(Line line) {
        Field field = ReflectionUtils.findField(Line.class, "id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, line, ++seq);
        return line;
    }

    public List<Line> findAll() {
        return null;
    }

    public void deleteById(Long lineId) {
        lines.stream()
                .filter(line -> line.getId() == lineId)
                .findFirst()
                .ifPresent( line -> lines.remove(line) );
    }
}
