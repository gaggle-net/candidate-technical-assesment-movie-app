package net.gaggle.challenge.model;

import java.util.Collection;

/**
 * Represents all the people a person worked with.
 */
public class Colleagues {

    /**
     * The person for who we would like to know their colleagues
     */
    private Person person;

    /**
     * What people did this person work with?
     */
    private Collection<Person> colleagues;


    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public Collection<Person> getColleagues() {
        return colleagues;
    }

    public void setColleagues(final Collection<Person> colleagues) {
        this.colleagues= colleagues;
    }
}
