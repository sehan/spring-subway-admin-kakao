package subway.web.station;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import subway.core.NotFoundStationException;
import subway.core.Station;
import subway.core.StationRegistry;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

abstract class StationServiceTest {

    StationRegistry service;

}


class DefaultStationRegistryTest extends StationServiceTest {

    @Mock
    StationDao dao;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        service = new DefaultStationRegistry(dao);
    }

    @DisplayName("ID 로 역을 찾을 수 없으면 exception 을 던진다")
    @Test
    void notExistId(){
        Long notExistId = 0L;
        assertThatThrownBy(() -> service.findOne(notExistId) )
                .isInstanceOf(NotFoundStationException.class);
    }

    @DisplayName("ID 로 역을 찾을 수 있다")
    @Test
    void get(){
        // Given
        Long id = 1L;
        Mockito.when(dao.findById(id))
                .thenReturn(Optional.of(new Station(1L, "서천역")));

        // When
        // Then
        assertThat(service.findOne(id)).isEqualTo(new Station(1L, "서천역"));
    }


}
