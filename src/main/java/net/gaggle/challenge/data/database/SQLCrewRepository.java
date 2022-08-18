package net.gaggle.challenge.data.database;

import net.gaggle.challenge.data.CrewRepository;
import net.gaggle.challenge.data.MovieRepository;
import net.gaggle.challenge.data.PersonRepository;
import net.gaggle.challenge.model.Collaboration;
import net.gaggle.challenge.model.Credits;
import net.gaggle.challenge.model.Crew;
import net.gaggle.challenge.model.CrewRole;
import net.gaggle.challenge.model.Movie;
import net.gaggle.challenge.model.MovieRoleTuple;
import net.gaggle.challenge.model.Person;
import net.gaggle.challenge.model.PersonRoleTuple;
import net.gaggle.challenge.model.Resume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
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
     * Query to get all the people a person has collaborated with
     */
    private static final String QUERY_CREW_FOR_COLLABORATION = "select distinct person from crew where crew.movie in (select movie from crew where crew.person = :personid) and crew.person != :personid";

    /**
     * Where to go to deserialize Movie objects.
     */
    @Autowired
    private MovieRepository movieRepository;

    /**
     * Where to go to deserialize Person objects.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * Spring's interface for SQL.
     */
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public Collection<Crew> everything() {
        return jdbcTemplate.query(QUERY_ALL_CREW, new CrewRowMapper(personRepository, movieRepository));
    }

    @Override
    public Resume moviesFor(final Long personId) {
        final Resume result = new Resume();

        final Optional<Person> person = personRepository.findById(personId);
        if (!person.isPresent()) {
            return result;
        }
        result.setPerson(person.get());

        final Set<MovieRoleTuple> jobs = new HashSet<>();
        result.setJobs(jobs);


        final Map<String, Object> varsMap = new HashMap<String, Object>();
        varsMap.put("personid", personId);


        final SqlRowSet rs = jdbcTemplate.queryForRowSet(QUERY_CREW_FOR_PERSON, varsMap);
        while (rs.next()) {
            try {
                final MovieRoleTuple current = new MovieRoleTuple();
                final long movieId = rs.getInt("movie");
                LOG.info("finding movieid={}", movieId);
                final Optional<Movie> movie = movieRepository.findById(movieId);

                if (movie.isPresent()) {
                    current.setMovie(movie.get());
                    current.setRole(CrewRole.valueOf(rs.getString("role")));
                    jobs.add(current);
                }
            } catch (Exception se) {
                LOG.debug("failed to find movie", se);
                //move on to the next movie
            }
        }

        return result;
    }

    @Override
    public Credits peopleFor(final Long movieId) {

        final Credits result = new Credits();

        final Optional<Movie> movie = movieRepository.findById(movieId);
        if (!movie.isPresent()) {
            return result;
        }
        result.setMovie(movie.get());

        final Set<PersonRoleTuple> jobs = new HashSet<>();
        result.setCrew(jobs);


        final Map<String, Object> varsMap = new HashMap<String, Object>();
        varsMap.put("movieid", movieId);


        final SqlRowSet rs = jdbcTemplate.queryForRowSet(QUERY_CREW_FOR_MOVIE, varsMap);
        while (rs.next()) {
            try {
                final PersonRoleTuple current = new PersonRoleTuple();

                final long personId = rs.getLong("person");
                LOG.info("finding person={}", personId);
                final Optional<Person> person = personRepository.findById(personId);
                if (person.isPresent()) {
                    current.setPerson(person.get());
                    current.setRole(CrewRole.valueOf(rs.getString("role")));
                    jobs.add(current);
                }
            } catch (Exception se) {
                LOG.debug("failed to find person", se);
                //move on to the next person
            }
        }

        return result;

    }

    @Override
    public Collaboration collaboratedFor(final Long personId) {
        final Collaboration result = new Collaboration();

        final Optional<Person> person = personRepository.findById(personId);
        if (!person.isPresent()) {
            return result;
        }
        result.setPerson(person.get());

        final ArrayList<Person> collaborations = new ArrayList<>();
        result.setCollaborators(collaborations);


        final Map<String, Object> varsMap = new HashMap<String, Object>();
        varsMap.put("personid", personId);


        final SqlRowSet rs = jdbcTemplate.queryForRowSet(QUERY_CREW_FOR_COLLABORATION, varsMap);
        while (rs.next()) {
            try {
                final long collaboratorId = rs.getLong("person");
                LOG.info("=== collaborator personid={}: ", collaboratorId);
                final Optional<Person> collaboratingPerson = personRepository.findById(collaboratorId);
                if (collaboratingPerson.isPresent()) {
                    collaborations.add(collaboratingPerson.get());
                }
            } catch (Exception se) {
                LOG.info("failed to find linked person", se);
                //move on to the next person
            }
        }

        return result;
    }
}
