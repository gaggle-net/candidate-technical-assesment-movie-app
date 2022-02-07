package net.gaggle.challenge.model;

import java.util.Collection;

/**
 * Represents a single person's body of work.
 */
public class Resume {

    /**
     * Who is this Resume about?
     */
    private Person person;

    /**
     * What movies did this person work on, and what did they d0?
     */
    private Collection<MovieRoleTuple> jobs;


    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public Collection<MovieRoleTuple> getJobs() {
        return jobs;
    }

    public void setJobs(final Collection<MovieRoleTuple> jobs) {
        this.jobs = jobs;
    }
}
