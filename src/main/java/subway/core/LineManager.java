package subway.core;

import java.util.List;

public interface LineManager {

    /**
     * 라인 생성
     *
     * 제약사항
     * 1. 라인이름이 이미 존재하면 AlreadyExistLineNameException 이 발생한다
     *
     * @param newLine
     * @param newSection
     * @return
     */
    Line create(Line newLine, Section newSection);

    void delete(Long lineId);

    Line findOne(Long lineId);

    List<Line> getAll();

    @Deprecated
    Line addSection(Long lineId, Section section);

    Line addSection(Long lineId, Station upStation, Station downStation, long distance);

    Line removeSection(Long lineId, Station station);

}
