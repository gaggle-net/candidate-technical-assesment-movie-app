package net.gaggle.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represnts a role in a given movie.  The {@link Person} who did this role is assumed to be in a containing {@link Resume} object.
 *
 * @see Resume
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieRoleTuple {

    /**
     * The Movie this role was for.
     */
    private Movie movie;

    /**
     * The role this person filled.
     */
    private CrewRole role;

}
