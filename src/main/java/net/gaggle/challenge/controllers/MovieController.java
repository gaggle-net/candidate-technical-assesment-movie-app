package net.gaggle.challenge.controllers;

import net.gaggle.challenge.data.MovieRepository;
import net.gaggle.challenge.model.Movie;
import net.gaggle.challenge.services.AuditLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST resources for manipulating {@link Movie}s.
 */
@RestController
@RequestMapping("movies")
public class MovieController {

    /**
     * LOGGER.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MovieController.class);

    /**
     * Our connection to the data.
     */
    @Autowired
    private MovieRepository movieRepository;


    /**
     *
     */
    @Autowired
    private AuditLog auditLog;

    /**
     * Lists out all the movies in the database.
     *
     * @return a List of movie objects.
     */
    @GetMapping("/all")
    public List<Movie> allMovies() {
        return auditLog.auditAction("/movies/all", () -> {
            final ArrayList<Movie> results = new ArrayList<>();
            final Iterable<Movie> examples = movieRepository.findAll();
            examples.forEach(results::add);
            LOG.info("returning allMovies={}", results.size());
            return results;
        });
    }


    /**
     * Find a specific movie based on unique Id.
     * @param movieId the movie we want to know about.
     * @return A Movie object if the movie exists; empty if not.
     */
    @GetMapping("/id/{movieId}")
    public Optional<Movie> movieBy(@PathVariable final Long movieId) {
        return auditLog.auditAction("/movies/by-id", () -> {
            LOG.info("looking for movieId={}", movieId);
            Optional<Movie> result = movieRepository.findById(movieId);
            LOG.info("found={}", result);
            return result;
        });
    }


}
