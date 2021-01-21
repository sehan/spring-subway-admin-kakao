package subway.core;

import java.util.List;

public interface LineManager {
    Line create(Line newLine, Section newSection);

    void delete(Long lineId);

    Line findOne(Long lineId);

    List<Line> getAll();

    Line addSection(Long lineId, Section section);

    Line removeSection(Long lineId, Station station);

}
