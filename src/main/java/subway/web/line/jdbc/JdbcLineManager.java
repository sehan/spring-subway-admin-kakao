package subway.web.line.jdbc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.core.Line;
import subway.core.LineManager;
import subway.core.Section;
import subway.core.Station;

import java.util.List;

@Service
public class JdbcLineManager implements LineManager {

    private LineTemplate lineTemplate;
    private SectionTemplate sectionTemplate;

    public JdbcLineManager(LineTemplate lineTemplate, SectionTemplate sectionTemplate) {
        this.lineTemplate = lineTemplate;
        this.sectionTemplate = sectionTemplate;
    }

    @Transactional
    @Override
    public Line create(Line newLine, Section newSection) {
        newLine = lineTemplate.save(newLine);
        sectionTemplate.save(newLine, newSection);
        return newLine;
    }

    @Override
    public List<Line> getAll() {
        return lineTemplate.findAll();
    }

    @Override
    public void delete(Long lineId) {
        sectionTemplate.deleteByLineId(lineId);
    }

    @Override
    public Line findOne(Long lineId) {
        return lineTemplate.findById(lineId);
    }

    @Override
    public Line addSection(Long lineId, Section section) {
        Line line = lineTemplate.findById(lineId);
        if( !line.hasStation(section.getUpStation()) && !line.hasStation(section.getDownStation()) ){
            throw new RuntimeException("상하행 역중 적어도 하나는 노선의 역과 연결되어 있어야 합니다");
        }
//        line.addSection(Section.of(up, down, distance));

        return null;
    }

    @Override
    public Line removeSection(Long lineId, Station station) {
        return null;
    }
}
