package net.gaggle.challenge.controllers;

import net.gaggle.challenge.data.PersonRepository;
import net.gaggle.challenge.model.Person;
import net.gaggle.challenge.services.AuditLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * REST resources dealing with {@link Person}s.
 */
@RestController
@RequestMapping("people")
public class PersonController {

    /**
     * LOGGER.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    /**
     * Our connection to the data.
     */
    @Autowired
    private PersonRepository personRepository;


    /**
     * Records Execution times and operations
     */
    @Autowired
    private AuditLog auditLog;


    /**
     * Lists out all examples present in the database.
     *
     * @return a List of user objects.
     */
    @GetMapping("/all")
    public List<Person> allPeople() {
        return auditLog.auditAction("/people/all", () -> {
            final ArrayList<Person> results = new ArrayList<>();
            final Iterable<Person> examples = personRepository.findAll();
            examples.forEach(results::add);
            LOG.info("returning allPeople={}", results.size());
            return results;
        });
    }


}
