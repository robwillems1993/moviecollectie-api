package nl.novi.eindopdrachtbackend.moviecollectieapi.repositories;

import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MoviePosterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoviePosterRepository extends JpaRepository<MoviePosterEntity, Long> {
    Optional<MoviePosterEntity> findByMovie_Id(Long movieId);

    long deleteByMovie_Id(Long movieId);
}
