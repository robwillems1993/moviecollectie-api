package nl.novi.eindopdrachtbackend.moviecollectieapi.repositories;

import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MoviePosterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviePosterRepository extends JpaRepository<MoviePosterEntity, Long> {
    MoviePosterEntity findByMovieId(Long movieId);
}
