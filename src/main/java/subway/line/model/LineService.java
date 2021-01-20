package subway.line.model;

import java.util.List;

public interface LineService {
    Line create(Line newLine, Section newSection);

    List<Line> getAll();

    void delete(Long lineId);

    Line get(Long lineId);
}
