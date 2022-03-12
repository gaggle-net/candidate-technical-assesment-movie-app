package net.gaggle.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A tuple for a single role a person did.  The {@link Movie} this was a part of is assumed to be part of a containing
 * {@link Credits} object.
 *
 * @see Credits
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonRoleTuple {

    /**
     * The role they performed.
     */
    private CrewRole role;

    /**
     * The Person in question.
     */
    private Person person;

}
