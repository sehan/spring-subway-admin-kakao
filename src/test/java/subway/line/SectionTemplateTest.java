package subway.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import subway.line.jdbc.SectionTemplate;
import subway.line.model.Line;
import subway.line.model.Section;
import subway.station.Station;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"/schema.sql"})
class SectionTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    SectionTemplate template;

    @BeforeEach
    void setUp(){
        template = new SectionTemplate(jdbcTemplate);
    }


    @Test
    void 섹션_저장(){
        Line line = Line.of(1L,"인덕원선","blue", null);
        Section section = Section.of(5L, 6L, 10);
        Section persist = template.save(line, section);

        assertThat(persist)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("upStation", Station.ref(5L))
                .hasFieldOrPropertyWithValue("downStation", Station.ref(6L))
                .hasFieldOrPropertyWithValue("distance", 10L);
    }

}