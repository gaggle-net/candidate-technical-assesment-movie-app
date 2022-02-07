package net.gaggle.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring boot entry point for the challenge application.
 */
@SpringBootApplication
@EnableScheduling
public class ChallengeApplication {

    /**
     * Just because it's spring, doesn't mean we don't need a main.
     *
     * @param args command line arguments, passed along to the {@link SpringApplication}.
     */
    public static void main(final String[] args) {
        SpringApplication.run(ChallengeApplication.class, args);
    }

}
