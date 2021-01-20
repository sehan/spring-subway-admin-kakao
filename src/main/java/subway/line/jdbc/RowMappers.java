package subway.line.jdbc;

import org.springframework.jdbc.core.RowMapper;
import subway.station.StationEntity;

public class RowMappers {

    public static RowMapper<LineEntity> line = (rs, rowNum) -> LineEntity.persistInstance(
            rs.getBigDecimal("id").longValue(),
            rs.getString("name"),
            rs.getString("color")
    );


    public static RowMapper<SectionEntity> section = (rs, rowNum) -> new SectionEntity(
            rs.getBigDecimal("id").longValue(),
            rs.getBigDecimal("line_id").longValue(),
            rs.getBigDecimal("up_station_id").longValue(),
            rs.getBigDecimal("down_station_id").longValue(),
            rs.getBigDecimal("distance").longValue()
    );

    public static RowMapper<StationEntity> station = (rs, rowNum) -> new StationEntity(
            rs.getBigDecimal("id").longValue(),
            rs.getString("name")
    );
}
