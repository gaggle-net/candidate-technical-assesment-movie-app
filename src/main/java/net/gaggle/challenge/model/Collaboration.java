package net.gaggle.challenge.model;

import java.util.ArrayList;

/**
 * Represents a single person's list of collaborations.
 */
public class Collaboration {

    /**
     * Who is this Collaboration about?
     */
    private Person person;

    /**
     * Who did this person collaborate with?
     */
    private ArrayList<Person> collaborators;


    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public ArrayList<Person> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(final ArrayList<Person> collaborators) {
        this.collaborators = collaborators;
    }
}
