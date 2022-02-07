package net.gaggle.challenge.data.database;

import net.gaggle.challenge.data.MovieRepository;
import net.gaggle.challenge.data.PersonRepository;
import net.gaggle.challenge.model.Crew;
import net.gaggle.challenge.model.CrewRole;
import net.gaggle.challenge.model.Movie;
import net.gaggle.challenge.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps rows from the Crew table to Crew objects, construing Person and Movie objects as a bonus.
 */
public class CrewRowMapper implements RowMapper<Crew> {

    /**
     * Where to go to deserialize Person objects.
     */
    private final PersonRepository personRepository;

    /**
     * Where to go to deserialize Movie objects.
     */
    private final MovieRepository movieRepository;

    /**
     * The query for this rowmapper only returns the crew records, so we have to go look up the Movie and Person objects ourselves.
     * (This way we only have one place for deserialization code).
     *
     * @param personRepository where to get Persons.
     * @param movieRepository  where to get Movies.
     */
    public CrewRowMapper(final PersonRepository personRepository, final MovieRepository movieRepository) {
        this.personRepository = personRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public Crew mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Crew result = new Crew();

        final long movieId = rs.getLong("movie");
        final long personId = rs.getLong("person");

        final Movie movie = movieRepository.findById(movieId).orElse(new Movie());

        final Person person = personRepository.findById(personId).orElse(new Person());

        result.setMovie(movie);
        result.setPerson(person);
        result.setRole(CrewRole.valueOf(rs.getString("role")));

        return result;
    }
}
