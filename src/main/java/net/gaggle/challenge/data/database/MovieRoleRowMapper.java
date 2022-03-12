package net.gaggle.challenge.data.database;

import lombok.RequiredArgsConstructor;
import net.gaggle.challenge.data.MovieRepository;
import net.gaggle.challenge.model.Crew;
import net.gaggle.challenge.model.CrewRole;
import net.gaggle.challenge.model.Movie;
import net.gaggle.challenge.model.MovieRoleTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MovieRoleRowMapper implements RowMapper<MovieRoleTuple> {

    private static final Logger LOG = LoggerFactory.getLogger(MovieRoleRowMapper.class);

    private final MovieRepository movieRepository;

    @Override
    public MovieRoleTuple mapRow(ResultSet rs, int rowNum) throws SQLException {

        final long movieId = rs.getLong(Crew.movieColumn);

        final Movie movie = movieRepository.findById(movieId).orElseGet(() -> {
            LOG.warn("No movie found for movieId={}, defaulting to empty", movieId);
            return new Movie();
        });

        return new MovieRoleTuple(
                movie,
                CrewRole.valueOf(rs.getString("role"))
        );
    }
}
