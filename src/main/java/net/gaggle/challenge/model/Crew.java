package net.gaggle.challenge.model;

/**
 * A container holding what a person did for one movie.
 */
public class Crew {

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


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
    }

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
