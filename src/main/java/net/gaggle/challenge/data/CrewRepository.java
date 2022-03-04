package net.gaggle.challenge.data;

import net.gaggle.challenge.model.Credits;
import net.gaggle.challenge.model.Crew;
import net.gaggle.challenge.model.Resume;
import net.gaggle.challenge.model.PersonRoleTuple;

import java.util.HashMap;
import java.util.Collection;

/**
 * A repository that knows about the crews for movies.
 */
public interface CrewRepository {


    /**
     * Returns every Crew relation in the database.
     *
     * @return a Collection of populated Crew objects.
     */
    Collection<Crew> everything();

    /**
     * Constructs the whole resume for a given person.
     *
     * @param personId The id of the person we care about.
     * @return A Resume, including all movies and the person's role.
     */
    Resume moviesFor(Long personId);

    /**
     * returns the entire credits for one movie.
     *
     * @param movieId the movie we want to know about
     * @return a Credits objects, including all people and their roles.
     */
    Credits peopleFor(Long movieId);

    /**
     * returns a Map of every Person who has worked with a given person in any movie.
     *
     * @param personId The id of the person we are looking for the colleagues of.
     * @param includeThemself whether the given person should be included in the results
     * @return A Map of ids linked to the Person objects for people who worked with the given person. 
     */
    HashMap<Long, PersonRoleTuple> colleaguesOf(final Long personId, boolean includeThemself);
}
