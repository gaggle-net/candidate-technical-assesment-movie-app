package net.gaggle.challenge.data.database;

import net.gaggle.challenge.data.CrewRepository;
import net.gaggle.challenge.data.MovieRepository;
import net.gaggle.challenge.data.PersonRepository;
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

import javax.sql.DataSource;
import java.util.*;

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
                final long movieId = rs.getLong("movie");
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
    public Collection<Person> personsFor(final Long personId) {
        final Collection<Person> result = new HashSet<Person>();

        final Optional<Person> person = personRepository.findById(personId);
        if (!person.isPresent()) {
            return result;
        }
        final Resume resume = this.moviesFor(personId);
        final MovieRoleTuple[] jobs = resume.getJobs().toArray(new MovieRoleTuple[resume.getJobs().size()]);
        final ArrayList<MovieRoleTuple> _jobs = new ArrayList<MovieRoleTuple>();

        for (int i = 0; i < jobs.length; i++) {
            boolean isValid = true;
            for (MovieRoleTuple job: _jobs) {
                if (job.getMovie().getId() == jobs[i].getMovie().getId()) {
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                _jobs.add(jobs[i]);
            }
        }

        for (int i = 0; i< _jobs.size(); i++) {
            final Credits credits = this.peopleFor(_jobs.get(i).getMovie().getId());
            final PersonRoleTuple[] crews = credits.getCrew().toArray(new PersonRoleTuple[credits.getCrew().size()]);
            for (int j = 0; j < crews.length; j++) {
                boolean isValidPerson = !(personId == crews[j].getPerson().getId());
                if (isValidPerson) {
                    for (Person _person: result) {
                        if (_person.getId() == crews[j].getPerson().getId()) {
                            isValidPerson = false;
                            break;
                        }
                    }
                }

                if (isValidPerson)
                    result.add(crews[j].getPerson());
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
}
