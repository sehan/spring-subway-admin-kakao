package subway.line.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import subway.line.model.Line;
import subway.line.model.Section;
import subway.station.Station;
import subway.station.jdbc.StationTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"/schema.sql"})
class LineTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    LineTemplate lineTemplate;
    SectionTemplate sectionTemplate;
    StationTemplate stationTemplate;

    Line 인덕원선;
    Station 영통역;
    Station 청명역;
    Station 기흥역;

    Line 일호선;

    @BeforeEach
    void setUp(){

        lineTemplate = new LineTemplate(jdbcTemplate);
        sectionTemplate = new SectionTemplate(jdbcTemplate);
        stationTemplate = new StationTemplate(jdbcTemplate);

        영통역 = stationTemplate.save(new Station("영통역"));
        청명역 = stationTemplate.save(new Station("청명역"));
        기흥역 = stationTemplate.save(new Station("기흥역"));

        인덕원선 = lineTemplate.save(new Line("분당선", "yellow"));
        sectionTemplate.save(인덕원선, new Section(영통역.getId(), 청명역.getId(), 3));
        sectionTemplate.save(인덕원선, new Section(청명역.getId(), 기흥역.getId(), 5));

        일호선 = lineTemplate.save(new Line("일호선", "green"));
    }

    @Test
    void test(){
        Line line = new Line("인덕원선", "blue");
        Line actual = lineTemplate.save(line);

        assertThat(actual)
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("name", "인덕원선")
                .hasFieldOrPropertyWithValue("color", "blue");
    }

    @Test
    void findById(){
        Line line = lineTemplate.findById(인덕원선.getId());

        assertThat(line.getStations())
                .hasSize(3)
                .containsExactlyInAnyOrder(영통역,청명역,기흥역);
    }

    @Test
    void findAll(){
        List<Line> lines = lineTemplate.findAll();
        assertThat(lines).hasSize(2);
    }

}