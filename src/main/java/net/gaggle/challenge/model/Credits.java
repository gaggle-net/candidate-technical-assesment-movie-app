package net.gaggle.challenge.model;

import java.util.Collection;

public class Credits {

    /**
     * Who worked on this movie, and what they did.
     */
    private Collection<PersonRoleTuple> crew;
    /**
     * The Movie these credits are for.
     */
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
    }

    public Collection<PersonRoleTuple> getCrew() {
        return crew;
    }

    public void setCrew(final Collection<PersonRoleTuple> crew) {
        this.crew = crew;
    }
}
