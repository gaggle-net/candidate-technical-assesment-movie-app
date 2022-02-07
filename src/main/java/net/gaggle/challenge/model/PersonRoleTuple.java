package net.gaggle.challenge.model;

/**
 * A tuple for a single role a person did.  The {@link Movie} this was a part of is assumed to be part of a containing
 * {@link Credits} object.
 *
 * @see Credits
 */
public class PersonRoleTuple {

    /**
     * The role they performed.
     */
    private CrewRole role;

    /**
     * The Person in question.
     */
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public CrewRole getRole() {
        return role;
    }

    public void setRole(final CrewRole role) {
        this.role = role;
    }
}
