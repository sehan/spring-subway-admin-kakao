package subway.line.jdbc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.line.model.Line;
import subway.line.model.LineService;
import subway.line.model.Section;

import java.util.List;

@Service
public class JLineService implements LineService {

    private LineTemplate lineTemplate;
    private SectionTemplate sectionTemplate;

    public JLineService(LineTemplate lineTemplate, SectionTemplate sectionTemplate) {
        this.lineTemplate = lineTemplate;
        this.sectionTemplate = sectionTemplate;
    }

    @Transactional
    @Override
    public Line create(Line newLine, Section newSection) {
        newLine = lineTemplate.save(newLine);
        sectionTemplate.save(newLine, newSection);
        return get(newLine.getId());
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
    public Line get(Long lineId) {
        return lineTemplate.findById(lineId);
    }
}
