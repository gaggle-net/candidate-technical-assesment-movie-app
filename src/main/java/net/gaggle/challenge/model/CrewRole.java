package net.gaggle.challenge.model;

/**
 * What job did a person have in a given movie?
 */
public enum CrewRole {
    /**
     * Backed it on kickstarter.
     */
    BACKER,
    /**
     * Any part on camera.
     */
    CAST,
    /**
     * Directed the movie.
     */
    DIRECTOR,
    /**
     * Wrote the screenplay (including "story by" and other related credits).
     */
    WRITER,

    /**
     * Producers of all kinds, with all adjectives.
     */
    PRODUCER,

    /**
     * Something "behind the camera".
     */
    CREW,

    /**
     * Catch-all for anything else, since this is an example and not really the IMDB.
     */
    OTHER


}
