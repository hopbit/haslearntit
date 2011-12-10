package it.haslearnt.entry;

import it.haslearnt.cassandra.mapping.Column;
import it.haslearnt.cassandra.mapping.Entity;
import it.haslearnt.cassandra.mapping.EntityWithGeneratedId;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity("Entries")
public class Entry extends EntityWithGeneratedId {

    @Column("skill")
    private String skill;

    @Column("when")
    private String when;

    @Column("difficulty")
    private String difficulty;
    
    private boolean completed;

    public String what() {
        return skill;
    }

    public String when() {
        return when;
    }

    public String howDifficult() {
        return difficulty;
    }

    public Entry iveLearnt(String skill) {
        this.skill = skill;
        return this;
    }

    public Entry today() {
        return when("today");
    }

    public Entry andItWas(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }

    public Entry when(String when) {
        this.when = when;
        return this;
    }

    public void andIveCompleted() {
        this.completed = true;
    }


    public boolean isCompleted() {
        return this.completed;
    }
}
