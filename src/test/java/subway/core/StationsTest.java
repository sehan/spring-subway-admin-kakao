package subway.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StationsTest {

    Station 수원역 = Station.of("수원역");
    Station 망포역 = Station.of("망포역");
    Station 영통역 = Station.of("영통역");
    Station 서천역 = Station.of("서천역");
    Station 동탄역 = Station.of("동탄역");

    @DisplayName("역 목록은 노선 순서대로 정렬된다")
    @Test
    void sortedStations(){
        // 수원역 -> 망포역 -> 영통역 -> 서천역 -> 동탄역
        Stations stations = new Stations();
        stations.add(영통역, 서천역);
        stations.add(서천역, 동탄역);
        stations.add(망포역, 영통역);
        stations.add(수원역, 망포역);

        assertThat(stations.toList())
                .containsExactly(수원역, 망포역, 영통역, 서천역, 동탄역 );
    }

    @DisplayName("두역중간에 역추가 ( 상행이 추가역 )")
    @Test
    void addStation1(){
        Stations stations = new Stations();
        stations.add(수원역, 영통역);

        stations.add(망포역, 영통역);

        assertThat(stations.toList())
                .containsExactly(수원역, 망포역, 영통역);
    }

    @DisplayName("두역중간에 역추가 ( 하행이 추가역 )")
    @Test
    void addStation2(){
        Stations stations = new Stations();
        stations.add(수원역, 영통역);

        stations.add(영통역, 서천역);

        assertThat(stations.toList())
                .containsExactly(수원역, 영통역, 서천역);
    }

    @DisplayName("상행종점 역추가 ")
    @Test
    void addStationUpLast(){
        Stations stations = new Stations();
        stations.add(망포역, 영통역);

        stations.add(수원역, 망포역);

        assertThat(stations.toList())
                .containsExactly(수원역, 망포역, 영통역);
    }

    @DisplayName("하행종점 역추가 ")
    @Test
    void addStationDownLast(){
        Stations stations = new Stations();
        stations.add(망포역, 영통역);

        stations.add(영통역, 서천역);

        assertThat(stations.toList())
                .containsExactly(망포역, 영통역, 서천역);
    }

}
