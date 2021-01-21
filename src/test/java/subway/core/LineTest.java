package subway.core;

import org.junit.jupiter.api.BeforeEach;

class LineTest {

    Line line;

    @BeforeEach
    void setUp(){
        line = Line.of(1L, "인덕원선", "blue",Section.of(Station.of(1L, "인덕원역"), Station.of(2L, "동탄역"), 100L));
    }



}