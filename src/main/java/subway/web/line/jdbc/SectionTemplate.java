package subway.web.line.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import subway.core.Line;
import subway.core.Section;

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
    public Section save(Line newLine, Section newSection) {
        SectionEntity sectionEntity = SectionEntity.of(newLine, newSection);

        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .usingGeneratedKeyColumns("id")
                .withTableName("SECTION")
                .usingColumns("line_id", "up_station_id", "down_station_id", "distance");

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(sectionEntity);
        Number id = insert.executeAndReturnKey(parameterSource);
        return findById(id.longValue());
    }

    public Section findById(long sectionId) {
        SectionEntity entity = template.queryForObject("select * from SECTION where id = ?",
                entityMapper,
                sectionId);
        return entity.toModel();
    }

    public void deleteByLineId(Long lineId) {
        template.update("delete from SECTION where line_id = ?", lineId);
    }
}
