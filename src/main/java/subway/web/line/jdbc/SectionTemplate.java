package subway.web.line.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import subway.core.Line;
import subway.core.Section;
import subway.web.station.StationEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SectionTemplate {

    private JdbcTemplate template;

    private RowMapper<SectionEntity> entityMapper = (rs, rowNum) -> new SectionEntity(
            rs.getBigDecimal("id").longValue(),
            rs.getBigDecimal("line_id").longValue(),
            rs.getBigDecimal("up_station_id").longValue(),
            rs.getBigDecimal("down_station_id").longValue(),
            rs.getBigDecimal("distance").longValue()
    );

    public SectionTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Transactional
    public Section save(Line line, Section newSection) {
        SectionEntity sectionEntity = SectionEntity.of(line.getId(), newSection);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .usingGeneratedKeyColumns("id")
                .withTableName("SECTION")
                .usingColumns("line_id", "up_station_id", "down_station_id", "distance");

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(sectionEntity);
        Number id = insert.executeAndReturnKey(parameterSource);
        return findById(id.longValue());
    }

    private long save(SectionEntity entity) {
        if (entity.hasId()) {
            return update(entity);
        } else {
            return insert(entity);
        }
    }

    private long insert(SectionEntity entity) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .usingGeneratedKeyColumns("id")
                .withTableName("SECTION")
                .usingColumns("line_id", "up_station_id", "down_station_id", "distance");

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        return insert.executeAndReturnKey(parameterSource).longValue();
    }

    private long update(SectionEntity entity) {
        template.update("update section set up_station_id = ?, down_station_id = ?, distance = ? where id = ?",
                entity.getUpStationId(),
                entity.getDownStationId(),
                entity.getDistance(),
                entity.getId()
        );
        return entity.getId();
    }

    @Transactional
    public void saveAll(Line line, List<Section> sections) {
        for (Section section : sections) {
            save(SectionEntity.of(line.getId(), section));
        }
    }

    public Section findById(long sectionId) {
        SectionEntity entity = template.queryForObject("select * from SECTION where id = ?",
                entityMapper,
                sectionId);

        List<Long> stationIds = Arrays.asList(entity.getUpStationId(), entity.getDownStationId());
        NamedParameterJdbcTemplate ntemplate = new NamedParameterJdbcTemplate(template);
        List<StationEntity> stations = ntemplate.query("select * from STATION where id in (:ids)",
                new MapSqlParameterSource("ids", stationIds),
                RowMappers.stationEntity);

        Map<Long, StationEntity> upAndDown = stations.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        return entity.toModel(upAndDown.get(entity.getUpStationId()), upAndDown.get(entity.getDownStationId()));
    }

    public void deleteByLineId(Long lineId) {
        template.update("delete from SECTION where line_id = ?", lineId);
    }

    public List<Section> findByLineId(Long lineId) {
        List<SectionEntity> entities = template.query("select * from SECTION where line_id = ?",
                RowMappers.sectionEntity,
                lineId);

        return entities.stream()
                .map(SectionEntity::toModel)
                .collect(Collectors.toList());
    }
}
