package subway.web.line.jdbc;

import org.springframework.jdbc.core.RowMapper;
import subway.core.Section;
import subway.core.Station;
import subway.web.station.StationEntity;

public class RowMappers {

    public static RowMapper<LineEntity> lineEntity = (rs, rowNum) -> LineEntity.persistInstance(
            rs.getBigDecimal("id").longValue(),
            rs.getString("name"),
            rs.getString("color")
    );


    public static RowMapper<SectionEntity> sectionEntity = (rs, rowNum) -> new SectionEntity(
            rs.getBigDecimal("id").longValue(),
            rs.getBigDecimal("line_id").longValue(),
            rs.getBigDecimal("up_station_id").longValue(),
            rs.getBigDecimal("down_station_id").longValue(),
            rs.getBigDecimal("distance").longValue()
    );

//    public static RowMapper<Section> section = (rs, rowNum) -> Section.of(
//            rs.getBigDecimal("id").longValue(),
//            Station.of(
//                    rs.getBigDecimal("up_station_id").longValue(),
//                    rs.getString("up_station_name")
//            ),
//            Station.of(
//                    rs.getBigDecimal("down_station_id").longValue(),
//                    rs.getString("down_station_name")
//            ),
//            rs.getBigDecimal("distance").longValue()
//    );

    public static RowMapper<StationEntity> stationEntity = (rs, rowNum) -> new StationEntity(
            rs.getBigDecimal("id").longValue(),
            rs.getString("name")
    );
}
