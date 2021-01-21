package subway.web.station;

import java.text.MessageFormat;

public class NotFoundStationException extends RuntimeException {
    public NotFoundStationException(Long stationId) {
        super(MessageFormat.format("존재하지 않는 역(id: {0})입니다", stationId));
    }
}
