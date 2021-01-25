package subway.core;

import java.util.List;

public interface SectionEventSupport {

    void onChangedSection(SectionEventListener listener );

    void notifySectionEvents(List<SectionEvent> events);

}
