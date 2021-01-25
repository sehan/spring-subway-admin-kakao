package subway.web.line.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.core.Line;
import subway.web.station.StationEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class LineTemplate {

    private JdbcTemplate template;

    public LineTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public Line save(Line newLine) {
        LineEntity entity = LineEntity.transientInstance(newLine);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .usingGeneratedKeyColumns("id")
                .withTableName("LINE")
                .usingColumns("name", "color");

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        Number id = insert.executeAndReturnKey(parameterSource);
        return findById(id.longValue(), false);
    }

    public Line findById(long lineId){
        return findById(lineId, true);
    }

    private Line findById(long lineId, boolean fetch) {
        LineEntity line = template.queryForObject("select * from LINE where id = ?",
                RowMappers.lineEntity,
                lineId);

        if( fetch ) {
            List<SectionEntity> sections = template.query("select * from SECTION where line_id = ?",
                    RowMappers.sectionEntity,
                    lineId);
            List<Long> stationIds = sections.stream()
                    .flatMap(section -> Stream.of(section.getUpStationId(), section.getDownStationId()))
                    .distinct()
                    .collect(Collectors.toList());
            NamedParameterJdbcTemplate ntemplate = new NamedParameterJdbcTemplate(template);
            List<StationEntity> stations = ntemplate.query("select * from STATION where id in (:ids)",
                    new MapSqlParameterSource("ids", stationIds),
                    RowMappers.stationEntity);

            return line.toModel(sections, stations);
        }
        return line.toModel();
    }

    public List<Line> findAll() {
        List<LineEntity> entities = template.query("select * from LINE", RowMappers.lineEntity);
        return entities.stream()
                .map(entity -> entity.toModel())
                .collect(Collectors.toList());
    }

    public void deleteById(Long lineId) {
        template.update("delete from LINE where id = ?", lineId);
    }

    public List<Line> findByName(String name) {
        List<LineEntity> entities = template.query("select * from LINE where name = ?",
                RowMappers.lineEntity,
                name);
        return entities.stream()
                .map(LineEntity::toModel)
                .collect(Collectors.toList());
    }
}
