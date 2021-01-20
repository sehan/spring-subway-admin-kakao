package subway.line.simple;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.line.model.Line;
import subway.line.model.LineService;
import subway.line.model.Section;
import subway.line.jdbc.SectionTemplate;

import java.util.List;

@Service
public class SimpleLineService implements LineService {

    private LineDao lineDao;
    private SectionTemplate sectionDao;

    public SimpleLineService(LineDao lineDao) {
        this.lineDao = lineDao;
    }

    @Transactional
    @Override
    public Line create(Line newLine, Section newSection) {

        newLine = lineDao.save(newLine);
        sectionDao.save(newLine, newSection);

        return newLine;
    }

    @Override
    public List<Line> getAll() {
        return lineDao.findAll();
    }

    @Override
    public void delete(Long lineId) {
        lineDao.deleteById(lineId);
    }

    @Override
    public Line get(Long lineId) {
        return null;
    }
}
