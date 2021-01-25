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

    @Transactional
    @Override
    public void delete(Long lineId) {
        sectionTemplate.deleteByLineId(lineId);
        lineTemplate.deleteById(lineId);
    }

    @Override
    public Line findOne(Long lineId) {
        return lineTemplate.findById(lineId);
    }

    @Deprecated
    @Transactional
    @Override
    public Line addSection(Long lineId, Section newSection) {
        Line line = lineTemplate.findById(lineId);
        line.addSection(newSection);
        return lineTemplate.findById(line.getId());
    }

    @Transactional
    @Override
    public Line addSection(Long lineId, Station upStation, Station downStation, long distance) {
        Line line = lineTemplate.findById(lineId);
        line.addSection(upStation, downStation, distance);
        sectionTemplate.saveAll(line, line.getSections());
        return lineTemplate.findById(line.getId());
    }

    @Transactional
    @Override
    public Line removeSection(Long lineId, Station station) {
        Line line = lineTemplate.findById(lineId);
        line.removeSection(station);
        return null;
    }
}
