package net.gaggle.challenge.model;

/**
 * Represnts a role in a given movie.  The {@link Person} who did this role is assumed to be in a containing {@link Resume} object.
 *
 * @see Resume
 */
public class MovieRoleTuple {

    /**
     * The Movie this role was for.
     */
    private Movie movie;

    /**
     * The role this person filled.
     */
    private CrewRole role;


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
    }

    public CrewRole getRole() {
        return role;
    }

    public void setRole(final CrewRole role) {
        this.role = role;
    }
}
