package net.gaggle.challenge.model;

import java.util.Collection;

/**
 * Contains a list of people who worked with a given Person and the Movies they worked on together.
 */
public class RelationMap {

    /**
     * Who is our central Person?
     */
    private Person person;

    /**
     * Who did this Person work with, and in what Movies?
     */
    private Collection<Colleague> colleagues;

    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public Collection<Colleague> getColleagues() {
        return colleagues;
    }

    public void setColleagues(final Collection<Colleague> colleagues) {
        this.colleagues = colleagues;
    }
}