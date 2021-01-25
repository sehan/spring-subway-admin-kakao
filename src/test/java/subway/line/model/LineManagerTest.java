package subway.line.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.core.AlreadyExistLineNameException;
import subway.core.Line;
import subway.core.LineManager;
import subway.core.Section;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


public abstract class LineManagerTest {

    protected LineManager service;

    @DisplayName("이름이 이미 존재하면 exception 을 던진다")
    @Test
    void existName(){
        Line 이미존재하는노선 = Line.of(1L, "이미존재하는노선이름", "black");
        Section 기본구간 = Section.of(1L, 2L, 10);

        assertThatThrownBy(() -> service.create(이미존재하는노선, 기본구간))
                .isInstanceOf(AlreadyExistLineNameException.class);
    }

}