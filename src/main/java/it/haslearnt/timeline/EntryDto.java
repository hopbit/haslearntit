package it.haslearnt.timeline;

public class EntryDto {

    private final String learntTime;

    public EntryDto(String learntTime) {
        this.learntTime = learntTime;
    }

    public String getLearntTime() {
        return learntTime;
    }
}
