package net.gaggle.challenge.data;

import net.gaggle.challenge.model.Credits;
import net.gaggle.challenge.model.Crew;
import net.gaggle.challenge.model.Resume;
import net.gaggle.challenge.model.Colleagues;

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
     * returns the colleagues for one person.
     *
     * @param personId The id of the person we care about.
     * @return a Colleagues objects, including all people they worked with.
     */
	Colleagues colleaguesFor(Long personId);


}
