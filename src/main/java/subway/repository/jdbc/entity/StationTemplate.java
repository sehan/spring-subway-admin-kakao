package subway.repository.jdbc.entity;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import subway.core.Station;

import java.util.List;

@Repository
public class StationTemplate {

    private JdbcTemplate template;

    private final RowMapper<Station> stationRowMapper = (rs, rowNum) -> Station.of(
            rs.getBigDecimal("id").longValue(),
            rs.getString("name")
    );

    public StationTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Transactional
    public Station save(Station station) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .usingGeneratedKeyColumns("id")
                .withTableName("STATION")
                .usingColumns("name");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", station.getName());

        Number id = insert.executeAndReturnKey(parameterSource);

        return findById(id.longValue());
    }

    public List<Station> findAll() {
        return template.query("select * from STATION", stationRowMapper);
    }

    public Station findById(Long stationId) {
        return template.queryForObject("select * from STATION where id = ?",
                stationRowMapper,
                stationId);
    }

    public void deleteById(Long stationId) {
        template.update("delete from STATION where id = ?", stationId);
    }
}
