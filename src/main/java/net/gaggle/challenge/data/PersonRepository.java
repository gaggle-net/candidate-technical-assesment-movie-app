package net.gaggle.challenge.data;

import net.gaggle.challenge.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring data jdbc repo for accessing the {@link Person} data store.
 * <p>
 * The actual guts of this are auto-generated, we need only define the interface.
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    //List<Person> findByMovie(Long movie);


}
