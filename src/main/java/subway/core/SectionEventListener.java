package subway.core;

import java.util.List;

public interface SectionEventListener {

    void handleEvent(List<SectionEvent> event);

}
