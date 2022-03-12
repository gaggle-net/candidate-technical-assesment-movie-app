package net.gaggle.challenge.data.database;

import lombok.RequiredArgsConstructor;
import net.gaggle.challenge.data.PersonRepository;
import net.gaggle.challenge.model.Crew;
import net.gaggle.challenge.model.Person;
import net.gaggle.challenge.model.PersonRoleTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class PersonRoleRowMapper implements RowMapper<PersonRoleTuple> {

    private static final Logger LOG = LoggerFactory.getLogger(PersonRoleRowMapper.class);

    /**
     * Where to go to deserialize Person objects.
     */
    private final PersonRepository personRepository;

    @Override
    public PersonRoleTuple mapRow(ResultSet rs, int rowNum) throws SQLException {
        final long personId = rs.getLong(Crew.personColumn);


        final Person person = personRepository.findById(personId).orElseGet(() -> {
            LOG.warn("No person found for personId={}, defaulting to empty", personId);
            return new Person();
        });

        return new PersonRoleTuple(
                null,
                person);
    }

}
