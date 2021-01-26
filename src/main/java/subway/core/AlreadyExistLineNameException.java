package subway.core;

import java.text.MessageFormat;

public class AlreadyExistLineNameException extends RuntimeException {
    public AlreadyExistLineNameException(String lineName) {
        super(MessageFormat.format("{0} 은 이미 존재하는 노선이름입니다", lineName));
    }
}
