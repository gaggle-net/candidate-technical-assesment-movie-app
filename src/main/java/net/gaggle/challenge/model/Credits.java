package net.gaggle.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Credits {

    /**
     * Who worked on this movie, and what they did.
     */
    private Collection<PersonRoleTuple> crew;
    /**
     * The Movie these credits are for.
     */
    private Movie movie;

}
