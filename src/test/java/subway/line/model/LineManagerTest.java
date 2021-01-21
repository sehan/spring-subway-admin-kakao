package subway.line.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.core.LineManager;


public abstract class LineManagerTest {

    LineManager service;

    @DisplayName("이름이 이미 존재하면 exception 을 던진다")
    @Test
    abstract void existName();
}