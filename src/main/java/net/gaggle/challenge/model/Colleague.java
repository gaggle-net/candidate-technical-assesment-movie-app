package net.gaggle.challenge.model;

import java.util.Collection;

/**
 * Represents a colleague of a Person.  The {@link Person} this is related to is specified by the containing
 * {@link RelationMap} object.
 */
public class Colleague {

    /**
     * Who is this colleague?
     */
    private Person person;

    /**
     * What movies did this colleague work on together with the target Person?
     */
    private Collection<String> movieTitles;

    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public Collection<String> getMovieTitles() { return movieTitles; }

    public void setMovieTitles(final Collection<String> movieTitles) {
        this.movieTitles = movieTitles;
    }
}