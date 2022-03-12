package net.gaggle.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A container holding what a person did for one movie.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Crew {

    public static final String movieColumn = "movie";

    public static final String personColumn = "person";

    /**
     * The Person who worked on the Movie.
     */
    private Person person;

    /**
     * The Movie the person worked on.
     */
    private Movie movie;

    /**
     * What that Person did on this Movie.
     */
    private CrewRole role;

}
