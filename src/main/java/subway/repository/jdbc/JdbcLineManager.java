package subway.repository.jdbc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.core.*;
import subway.repository.jdbc.entity.LineTemplate;
import subway.repository.jdbc.entity.SectionTemplate;

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
    public Line create(Line newLine, Station up, Station down, long distance) {
        shouldNotExistLineName(newLine.getName());
        newLine = lineTemplate.save(newLine);
        sectionTemplate.save(newLine.getId(), Section.of(up, down, distance));
        return findOne(newLine.getId());
    }

    private void shouldNotExistLineName(String lineName) {
        if (lineTemplate.findByName(lineName).size() > 0) {
            throw new AlreadyExistLineNameException(lineName);
        }
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
    public void removeSection(Long lineId, Station station) {
        Line line = lineTemplate.findById(lineId);
        line.onChangedSection(new DefaultSectionEventListener(lineId));
        line.removeSection(station);
    }

    @Override
    public Line update(Line line) {
        return lineTemplate.save(line);
    }

    class DefaultSectionEventListener implements SectionEventListener {

        private final long lineId;

        public DefaultSectionEventListener(long lineId) {
            this.lineId = lineId;
        }

        @Transactional
        @Override
        public void handleEvent(List<SectionEvent> events) {
            for (SectionEvent event : events) {
                if (event.isDelete()) {
                    sectionTemplate.deleteById(event.getSection().getId());
                }
                if (event.isUpdate()) {
                    sectionTemplate.save(lineId, event.getSection());
                }
            }
        }
    }
}
