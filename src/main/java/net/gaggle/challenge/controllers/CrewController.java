package net.gaggle.challenge.controllers;

import net.gaggle.challenge.data.CrewRepository;
import net.gaggle.challenge.model.Credits;
import net.gaggle.challenge.model.Crew;
import net.gaggle.challenge.model.Person;
import net.gaggle.challenge.model.Resume;
import net.gaggle.challenge.services.AuditLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * Rest controller for crew-related questions.
 */
@RestController
@RequestMapping("crew")
public class CrewController {


    /**
     * LOGGER.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CrewController.class);

    /**
     * Our connection to the data.
     */
    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private AuditLog auditLog;

    /**
     * Lists out all Crew present in the database.
     * This dumps the entire database, so this is meant for testing only.
     *
     * @return a List of Crew objects.
     */
    @GetMapping("/all")
    public Collection<Crew> allPeople() {
        return auditLog.auditAction("/crew/all", () -> {
            LOG.info("here come all the crews");
            final Collection<Crew> results = crewRepository.everything();
            return (results);
        });
    }


    /**
     * Return all the crew for a single movie.
     *
     * @param movieId the id of the movie we care about.
     * @return a Credits object representing the crew of this film.
     */
    @GetMapping("/movie/{movieId}")
    public Credits peopleFor(@PathVariable final Long movieId) {
            return auditLog.auditAction("/crew/movie/by-id", () -> {
                LOG.info("here come all the people in movie {}", movieId);
                //final Collection<Crew> results = crewRepository.crewFor(movieId);
                final Credits results = crewRepository.peopleFor(movieId);
                return results;
            });
    }

    /**
     * Returns a person's {@link Resume}, listing all of the {@link net.gaggle.challenge.model.Movie}s they took port in
     * and their {@link net.gaggle.challenge.model.CrewRole} in each movie.  With a Resume, the Person is only included once for the entire
     * response, instead of one for each movie, as would be the case with a collection of {@link Crew} obects.
     * <p>
     *
     * @param personId the unique ID of the person we want to know about.
     * @return a fully-populated Resume.
     */
    @GetMapping("/person/{personId}")
    public Resume moviesFor(@PathVariable final Long personId) {
        return auditLog.auditAction("/crew/person/by-id", () -> {
            LOG.info("here come all the movies for person {}", personId);
            final Resume results = crewRepository.moviesFor(personId);
            return results;
        });
    }

    /**
     * Returns a list of {@link Person}, listing all the actors they worked with in their career
     * <p>
     *
     * @param personId the unique ID of the person we want to know about.
     * @return a List containing Person objects.
     */
    @GetMapping("/costars/{personId}")
    public List<Person> coStarsFor(@PathVariable final long personId) {
        return auditLog.auditAction("/crew/coStars/by-id", () -> {
            LOG.info("here come all the co-stars for person {}", personId);
            final List<Person> results = crewRepository.coStarsFor(personId);
            return results;
        });
    }

}
