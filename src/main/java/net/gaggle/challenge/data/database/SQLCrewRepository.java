package net.gaggle.challenge.data.database;

import lombok.RequiredArgsConstructor;
import net.gaggle.challenge.data.CrewRepository;
import net.gaggle.challenge.data.MovieRepository;
import net.gaggle.challenge.data.PersonRepository;
import net.gaggle.challenge.model.Credits;
import net.gaggle.challenge.model.Crew;
import net.gaggle.challenge.model.Movie;
import net.gaggle.challenge.model.MovieRoleTuple;
import net.gaggle.challenge.model.Person;
import net.gaggle.challenge.model.PersonRoleTuple;
import net.gaggle.challenge.model.Resume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SQLCrewRepository implements CrewRepository {

    /**
     * LOGGER.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SQLCrewRepository.class);

    /**
     * Query to get everything in the crew table.
     */
    private static final String QUERY_ALL_CREW = "select * from crew";
    /**
     * Query to get all crew records for one Person.
     */
    private static final String QUERY_CREW_FOR_PERSON = "select * from crew where crew.person = :personid";
    /**
     * Query to get all crew records for one Movie.
     */
    private static final String QUERY_CREW_FOR_MOVIE = "select * from crew where crew.movie = :movieid";

    /**
     * Where to go to deserialize Movie objects.
     */
    private final MovieRepository movieRepository;

    /**
     * Where to go to deserialize Person objects.
     */
    private final PersonRepository personRepository;

    /**
     * Spring's interface for SQL.
     */
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final MovieRoleRowMapper roleMapper;

    @Autowired
    public SQLCrewRepository(
            final MovieRepository movieRepository,
            final PersonRepository personRepository,
            final DataSource dataSource
    ) {
        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.roleMapper = new MovieRoleRowMapper(movieRepository);
    }

    @Override
    public Collection<Crew> everything() {
        return jdbcTemplate.query(QUERY_ALL_CREW, new CrewRowMapper(personRepository, movieRepository));
    }

    @Override
    public Resume moviesFor(final Long personId) {

        LOG.debug("Getting movies for personId={}", personId);

        final Optional<Person> person = personRepository.findById(personId);
        if (!person.isPresent()) {
            return new Resume();
        }

        final Map<String, Object> varsMap = new HashMap<String, Object>();
        varsMap.put("personid", personId);

        final List<MovieRoleTuple> roles = jdbcTemplate
                .query(QUERY_CREW_FOR_PERSON, varsMap, roleMapper);

        return new Resume(
                person.get(),
                roles);
    }

    @Override
    public Credits peopleFor(final Long movieId) {

        LOG.debug("Getting people for movieId={}", movieId);

        final Optional<Movie> movie = movieRepository.findById(movieId);
        if (!movie.isPresent()) {
            return new Credits();
        }

        final Map<String, Object> varsMap = new HashMap<String, Object>();
        varsMap.put("movieid", movieId);

        final List<PersonRoleTuple> roles = jdbcTemplate
                .query(QUERY_CREW_FOR_MOVIE, varsMap, new PersonRoleRowMapper(personRepository));

        return new Credits(
                roles,
                movie.get());
    }
}
