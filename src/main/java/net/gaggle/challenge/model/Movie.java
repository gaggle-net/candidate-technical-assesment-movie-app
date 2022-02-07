package net.gaggle.challenge.model;

import org.springframework.data.annotation.Id;

public class Movie {
    /**
     * Unique key for the movie.
     */
    @Id
    private long id;
    /**
     * What is the movie called?.
     */
    private String title;

    /**
     * Year the movie was released.
     */
    private int year;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }
}
