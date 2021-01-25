package subway.core;

public interface SectionEvent {

    enum Type {
        DELETE, UPDATE;
    }

    Section getSection();

    Type getType();

    static SectionEvent of(Section section, Type type) {
        return new BaseEvent(section, type);
    }

    class BaseEvent implements SectionEvent{

        private Section section;
        private Type type;

        public BaseEvent(Section section, Type type) {
            this.section = section;
            this.type = type;
        }

        @Override
        public Section getSection() {
            return section;
        }

        @Override
        public Type getType() {
            return type;
        }
    }

}
