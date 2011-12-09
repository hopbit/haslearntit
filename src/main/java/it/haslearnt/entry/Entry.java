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
        this.when = "today";
        return this;
    }

    public Entry andItWas(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Entry other = (Entry) obj;
        return new EqualsBuilder()
                .append(skill, other.skill)
                .append(when, other.when)
                .append(difficulty, other.difficulty)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(skill)
                .append(when)
                .append(difficulty)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
