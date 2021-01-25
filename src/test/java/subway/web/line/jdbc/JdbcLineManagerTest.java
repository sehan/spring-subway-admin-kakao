package subway.web.line.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import subway.core.Line;
import subway.line.model.LineManagerTest;

import java.util.Arrays;

class JdbcLineManagerTest extends LineManagerTest {

    @Mock
    LineTemplate lineTemplate;

    @Mock
    SectionTemplate sectionTemplate;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        service = new JdbcLineManager(lineTemplate, sectionTemplate);

        Mockito.when(lineTemplate.findByName("이미존재하는노선이름"))
                .thenReturn(Arrays.asList(Line.of(1L, "이미존재하는노선이름", "black")));
    }

}