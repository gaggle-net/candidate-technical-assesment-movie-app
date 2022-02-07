package net.gaggle.challenge.model;

import org.springframework.data.annotation.Id;

/**
 * A single person.
 */
public class Person {

    /**
     * Unique key for the person.
     */
    @Id
    private long id;
    /**
     * Who is this miserable person?
     */
    private String name;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
