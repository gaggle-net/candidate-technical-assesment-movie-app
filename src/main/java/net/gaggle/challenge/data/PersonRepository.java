package net.gaggle.challenge.data;

import net.gaggle.challenge.model.Person;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring data jdbc repo for accessing the {@link Person} data store.
 * <p>
 * The actual guts of this are auto-generated, we need only define the interface.
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    //List<Person> findByMovie(Long movie);

    @Query("select distinct p.id, p.name from Crew a inner join Crew b on a.movie = b.movie and a.person != b.person join Person p on b.person = p.id where a.person = :personId order by p.id;")
    List<Person> collaboratedWith(@Param("personId") Long personId);
}
