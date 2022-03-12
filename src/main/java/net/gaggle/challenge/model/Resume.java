package net.gaggle.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * Represents a single person's body of work.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Resume {

    /**
     * Who is this Resume about?
     */
    private Person person;

    /**
     * What movies did this person work on, and what did they d0?
     */
    private Collection<MovieRoleTuple> jobs;

}
