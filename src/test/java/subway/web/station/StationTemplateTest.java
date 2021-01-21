package subway.web.station;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import subway.core.Station;
import subway.web.station.jdbc.StationTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"/schema.sql"})
class StationTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    StationTemplate dao;

    @BeforeEach
    void setUp(){
        dao = new StationTemplate(jdbcTemplate);
    }

    @Transactional
    @Test
    void test(){
        Station station = Station.of("서천역");
        Station actual = dao.save(station);

        assertThat(actual).isNotNull();
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "서천역");
    }


}