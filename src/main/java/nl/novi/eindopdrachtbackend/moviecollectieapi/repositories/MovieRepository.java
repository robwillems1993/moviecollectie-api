package nl.novi.eindopdrachtbackend.moviecollectieapi.repositories;

import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
}
