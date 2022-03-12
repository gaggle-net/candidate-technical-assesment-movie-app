package net.gaggle.challenge.data;

import net.gaggle.challenge.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository repository;

    private final Long hamillId = 1L;
    private final Long fischerId = 4L;
    private final Long belushiId = 5L;

    @Test
    public void collaborationExcludesSelf() {
        assertFalse(repository.collaboratedWith(hamillId)
                .stream()
                .map(Person::getId)
                .anyMatch(a -> a.equals(hamillId)));
    }

    @Test
    public void hamillCollaboratedWithFischer() {
        assertTrue(repository.collaboratedWith(hamillId)
                .stream()
                .map(Person::getId)
                .anyMatch(a -> a.equals(fischerId)));
    }

    @Test
    public void hamillDidNotCollaborateWithBelushi() {
        assertFalse(repository.collaboratedWith(hamillId)
                .stream()
                .map(Person::getId)
                .anyMatch(a -> a.equals(belushiId)));
    }

    @Test
    public void noCollaborationDuplicates() {
        final HashMap<Long, Integer> idCounts = new HashMap<>();
        for(final Person person : repository.collaboratedWith(hamillId)) {
            final int count = idCounts.getOrDefault(person.getId(), 0) + 1;
            idCounts.put(person.getId(), count);
        }
        assertFalse(idCounts.values().stream().anyMatch(count -> count > 1));
    }

}
