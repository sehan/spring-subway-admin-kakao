package subway.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

class SectionsTest {

    Station 수원역;
    Station 영통역;
    Station 서천역;

    @BeforeEach
    void setUp(){
        수원역 = Station.of("수원역");
        영통역 = Station.of("영통역");
        서천역 = Station.of("서천역");
    }

    @DisplayName("섹션이 1개는 있어야 객체를 생성할 수 있다")
    @Test
    void test() {
        assertThatThrownBy(() -> new Sections(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상행 종점 등록")
    @Test
    void addUpLast(){
        // Given
        Sections sections = new Sections(Section.of(영통역, 서천역, 10));

        // When
        Station 망포역 = Station.of("망포역");
        sections.addSection(망포역, 영통역, 5);

        // Then
        assertThat(sections.hasStation(망포역));
        assertThat(sections.getDownSectionOf(영통역))
                .isEqualTo(Section.of(영통역, 서천역, 10));
        assertThat(sections.getUpSectionOf(영통역))
                .isEqualTo(Section.of(망포역, 영통역, 5));
    }

    @DisplayName("하행 종점 등록")
    @Test
    void addDownLast(){
        // Given
        Sections sections = new Sections(Section.of(영통역, 서천역, 10));

        // When
        Station 동탄역 = Station.of("동탄역");
        sections.addSection(서천역, 동탄역, 5);

        // Then
        assertThat(sections.hasStation(동탄역));
        assertThat(sections.getDownSectionOf(서천역))
                .isEqualTo(Section.of(서천역, 동탄역, 5));
        assertThat(sections.getUpSectionOf(서천역))
                .isEqualTo(Section.of(영통역, 서천역, 10));
    }

    @DisplayName("구간 추가 ( 교차역이 하행인 경우 ) ")
    @Test
    void addSectionDown(){
        // Given
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);

        // When
        Station 망포역 = Station.of("망포역");
        sections.addSection(망포역, 영통역, 1);

        // Then
        assertThat(sections.hasStation(망포역));
        assertThat(sections.getUpSectionOf(망포역))
                .isEqualTo(Section.of(수원역, 망포역, 9));
        assertThat(sections.getDownSectionOf(망포역))
                .isEqualTo(Section.of(망포역, 영통역, 1));
        assertThat(sections.getDownSectionOf(영통역))
                .isEqualTo(Section.of(영통역, 서천역, 5));

    }

    @DisplayName("구간 추가 ( 교차역이 하행인 경우 ) 시 기존 구간보다 길이가 길면 exception 을 던진다")
    @Test
    void addSectionDownWithException(){
        // Given
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);

        // When
        // Then
        Station 망포역 = Station.of("망포역");
        assertThatThrownBy(() -> sections.addSection(망포역, 영통역, 11))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("구간 추가 ( 교차역이 상행인 경우 ) ")
    @Test
    void addSectionUp(){
        // Given
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);

        // When
        Station 경희역 = Station.of("경희역");
        sections.addSection(영통역, 경희역, 1);

        // Then
        assertThat(sections.hasStation(경희역));
        assertThat(sections.getUpSectionOf(영통역))
                .isEqualTo(Section.of(수원역, 영통역, 10));
        assertThat(sections.getUpSectionOf(경희역))
                .isEqualTo(Section.of(영통역, 경희역, 1));
        assertThat(sections.getDownSectionOf(경희역))
                .isEqualTo(Section.of(경희역, 서천역, 4));

    }

    @DisplayName("구간 추가 ( 교차역이 상행인 경우 ) 시 기존 구간보다 길이가 길면 exception 을 던진다")
    @Test
    void addSectionUpWithException(){
        // Given
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);

        // When
        // Then
        Station 망포역 = Station.of("망포역");
        assertThatThrownBy(() -> sections.addSection(망포역, 영통역, 11))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("교차역이 없으면 구간추가시 exception 을 던진다")
    @Test
    void noIntersection(){
        // Given
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);

        // When
        // Then
        Station 판교역 = Station.of("판교역");
        Station 분당역 = Station.of("분당역");
        assertThatThrownBy(() -> sections.addSection(판교역, 분당역, 1))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("이미 있는 구간을 추가하면 exception 을 던진다")
    @Test
    void existSection(){
        // Given
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));

        // When
        // Then
        assertThatThrownBy(() -> sections.addSection(수원역, 영통역, 1))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("노선에 존재하지 않는 역을 삭제하면 exception 을 던진다")
    @Test
    void notExistStation(){
        // Given
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);

        // When
        // Then
        assertThatThrownBy( () -> sections.removeSection(Station.of("판교역")) )
                .isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("구간 삭제 ( 삭제역이 상행 종점 인 경우 )")
    @Test
    void removeSectionUpLast(){
        // Given
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);

        // When
        sections.removeSection(수원역);

        // Then
        assertThat(sections.hasStation(수원역)).isFalse();
        assertThat(sections.getUpSectionOf(영통역)).isNull();
        assertThat(sections.getDownSectionOf(영통역))
                .isEqualTo(Section.of(영통역, 서천역, 5));

    }

    @DisplayName("구간 삭제 ( 삭제역이 상행 종점 인 경우 ) 시 Section Event 가 발생한다")
    @Test
    void eventWhenRemoveSectionUpLast() {
        // Given
        AtomicInteger countOfDeleteEvent = new AtomicInteger();
        AtomicInteger countOfUpdateEvent = new AtomicInteger();
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);
        sections.onChangedSection(events -> {
            countOfDeleteEvent.addAndGet((int) events.stream()
                    .filter(event -> event.getType().equals(SectionEvent.Type.DELETE))
                    .count());
            countOfUpdateEvent.addAndGet((int) events.stream()
                    .filter(event -> event.getType().equals(SectionEvent.Type.UPDATE))
                    .count());
        });

        // When
        sections.removeSection(수원역);

        // Then
        assertThat(countOfDeleteEvent.get()).isEqualTo(1);
        assertThat(countOfUpdateEvent.get()).isEqualTo(0);
    }

    @DisplayName("구간 삭제 ( 삭제역이 하행 종점 인 경우 )")
    @Test
    void removeSectionDownLast() {
        // Given
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);

        // When
        sections.removeSection(서천역);

        // Then
        assertThat(sections.hasStation(서천역)).isFalse();
        assertThat(sections.getUpSectionOf(영통역))
                .isEqualTo(Section.of(수원역, 영통역, 10));
        assertThat(sections.getDownSectionOf(영통역)).isNull();
    }

    @DisplayName("구간 삭제 ( 삭제역이 하행 종점 인 경우 ) 시 Section Event 가 발생한다")
    @Test
    void eventWhenRemoveSectionDownLast() {
        // Given
        AtomicInteger countOfDeleteEvent = new AtomicInteger();
        AtomicInteger countOfUpdateEvent = new AtomicInteger();
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);
        sections.onChangedSection(events -> {
            countOfDeleteEvent.addAndGet((int) events.stream()
                    .filter(event -> event.getType().equals(SectionEvent.Type.DELETE))
                    .count());
            countOfUpdateEvent.addAndGet((int) events.stream()
                    .filter(event -> event.getType().equals(SectionEvent.Type.UPDATE))
                    .count());
        });

        // When
        sections.removeSection(서천역);

        // Then
        assertThat(countOfDeleteEvent.get()).isEqualTo(1);
        assertThat(countOfUpdateEvent.get()).isEqualTo(0);
    }

    @DisplayName("구간 삭제 ( 중간역 )")
    @Test
    void removeMiddleSection() {
        // Given
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);

        // When
        sections.removeSection(영통역);

        // Then
        assertThat(sections.hasStation(영통역)).isFalse();
        assertThat(sections.getUpSectionOf(수원역)).isNull();
        assertThat(sections.getDownSectionOf(수원역))
                .isEqualTo(Section.of(수원역, 서천역, 15));

    }

    @DisplayName("구간 삭제 ( 중간역 ) 시 Section Event 가 발생한다")
    @Test
    void eventWhenRemoveMiddleSection() {
        // Given
        AtomicInteger countOfDeleteEvent = new AtomicInteger();
        AtomicInteger countOfUpdateEvent = new AtomicInteger();
        Sections sections = new Sections(Section.of(수원역, 영통역, 10));
        sections.addSection(영통역, 서천역, 5);
        sections.onChangedSection(events -> {
            countOfDeleteEvent.addAndGet((int) events.stream()
                    .filter(event -> event.getType().equals(SectionEvent.Type.DELETE))
                    .count());
            countOfUpdateEvent.addAndGet((int) events.stream()
                    .filter(event -> event.getType().equals(SectionEvent.Type.UPDATE))
                    .count());
        });

        // When
        sections.removeSection(영통역);

        // Then
        assertThat(countOfDeleteEvent.get()).isEqualTo(1);
        assertThat(countOfUpdateEvent.get()).isEqualTo(1);
    }
}