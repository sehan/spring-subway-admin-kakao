package subway.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import subway.core.Line;
import subway.core.Section;
import subway.core.Station;
import subway.repository.jdbc.entity.SectionTemplate;
import subway.repository.jdbc.entity.StationTemplate;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"/schema.sql"})
class SectionTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    SectionTemplate template;
    StationTemplate stationTemplate;

    Station 영통역;
    Station 서천역;
    Station 동탄역;
    Station 경희역;

    @BeforeEach
    void setUp() {
        template = new SectionTemplate(jdbcTemplate);
        stationTemplate = new StationTemplate(jdbcTemplate);

        영통역 = stationTemplate.save(Station.of("영통역"));
        경희역 = stationTemplate.save(Station.of("서천역"));
        서천역 = stationTemplate.save(Station.of("동탄역"));
        동탄역 = stationTemplate.save(Station.of("경희역"));

    }


    @Test
    void 섹션_저장() {
        Line line = Line.of(1L, "인덕원선", "blue");
        Section section = Section.of(영통역, 서천역, 10);
        Section persist = template.save(line, section);

        assertThat(persist)
                .hasFieldOrPropertyWithValue("upStation", 영통역)
                .hasFieldOrPropertyWithValue("downStation", 서천역)
                .hasFieldOrPropertyWithValue("distance", 10L);
    }

    @DisplayName("하나이상의 섹션저장 ( insert / update )")
    @Transactional
    @Test
    void 다수섹션_저장2() {
        // Given
        Section s1 = Section.of(영통역, 서천역, 5);
        Section s2 = Section.of(서천역, 동탄역, 5);

        Line line = Line.of(1L, "인덕원선", "blue");
        s1 = template.save(line, s1);
        s2 = template.save(line, s2);

        // When
        Section s3 = Section.of(경희역, 서천역, 2);
        s1.changeDownStation(경희역, 2);
        s2.changeUpStation(경희역, 2);

        template.saveAll(line, Arrays.asList(s1, s2, s3));

        // Then
        assertThat(template.findById(s1.getId()))
                .hasFieldOrPropertyWithValue("upStation", 영통역)
                .hasFieldOrPropertyWithValue("downStation", 경희역)
                .hasFieldOrPropertyWithValue("distance", 3L);

        assertThat(template.findById(s2.getId()))
                .hasFieldOrPropertyWithValue("upStation", 경희역)
                .hasFieldOrPropertyWithValue("downStation", 동탄역)
                .hasFieldOrPropertyWithValue("distance", 3L);

        assertThat(template.findByLineId(line.getId()))
                .hasSize(3);

    }



}