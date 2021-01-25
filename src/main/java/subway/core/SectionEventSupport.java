package subway.core;

public interface SectionEventSupport {

    void setSectionEventListener( SectionEventListener listener );

    void clearSectionEventListener();

    void notifySectionEvent(Section section, SectionEvent.Type type);

}
