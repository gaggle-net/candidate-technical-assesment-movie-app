package net.gaggle.challenge.data.database;

import net.gaggle.challenge.data.CrewRepository;
import net.gaggle.challenge.data.MovieRepository;
import net.gaggle.challenge.data.PersonRepository;
import net.gaggle.challenge.model.Colleague;
import net.gaggle.challenge.model.Credits;
import net.gaggle.challenge.model.Crew;
import net.gaggle.challenge.model.CrewRole;
import net.gaggle.challenge.model.Movie;
import net.gaggle.challenge.model.MovieRoleTuple;
import net.gaggle.challenge.model.Person;
import net.gaggle.challenge.model.PersonRoleTuple;
import net.gaggle.challenge.model.RelationMap;
import net.gaggle.challenge.model.Resume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

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
     * Query to find people who worked together
     */
    private static final String QUERY_COLLEAGUES_OF_PERSON = "select c.person, p.name, c.movie, m.title from crew c " +
            "join movie m on m.id = c.movie " +
            "join person p on p.id = c.person " +
            "where movie in (select movie from crew where person = :personid) " +
            "and person != :personid"; // Don't match the original Person with themselves

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
    public RelationMap colleaguesOf(final Long personId) {
        final RelationMap result = new RelationMap();

        final Optional<Person> person = personRepository.findById(personId);
        if (!person.isPresent()) {
            return result;
        }
        result.setPerson(person.get());

        final Set<Colleague> colleagues = new HashSet<>();
        result.setColleagues(colleagues);

        final Map<String, Object> varsMap = new HashMap<String, Object>();
        varsMap.put("personid", personId);

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(QUERY_COLLEAGUES_OF_PERSON, varsMap);

        while (rs.next()) {
            try {
                final long colleagueId = rs.getLong("person");
                Optional<Colleague> existing = colleagues.stream().filter(x -> x.getPerson().getId() == colleagueId).findFirst();

                if(existing.isPresent()) {
                    existing.get().getMovieTitles().add(rs.getString("title"));
                } else {
                    Colleague current = new Colleague();

                    // Create and add the new colleague's details
                    Person colleaguePerson = new Person();
                    colleaguePerson.setId(rs.getLong("person"));
                    colleaguePerson.setName(rs.getString("name"));
                    current.setPerson(colleaguePerson);

                    Collection<String> titles = new HashSet<>();
                    titles.add(rs.getString("title"));
                    current.setMovieTitles(titles);

                    colleagues.add(current);
                }
            } catch (Exception se) {
                LOG.debug("failed to read movie or person", se);
                //move on to the next record
            }
        }

        return result;
    }
}
