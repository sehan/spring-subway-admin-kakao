package subway.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SectionEventTest {

    @DisplayName("Delete 타입은 isDelete 시 true 를 return 한다")
    @Test
    void isDelete(){
        SectionEvent event = SectionEvent.of(Section.of(Station.of("역1"), Station.of("역2"), 1), SectionEvent.Type.DELETE);
        assertThat(event.isDelete()).isTrue();
    }

    @DisplayName("Update 타입은 isUpdate 시 true 를 return 한다")
    @Test
    void isUpdate(){
        SectionEvent event = SectionEvent.of(Section.of(Station.of("역1"), Station.of("역2"), 1), SectionEvent.Type.UPDATE);
        assertThat(event.isUpdate()).isTrue();
    }

}