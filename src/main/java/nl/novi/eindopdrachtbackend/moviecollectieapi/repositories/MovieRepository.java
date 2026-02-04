package nl.novi.eindopdrachtbackend.moviecollectieapi.repositories;

import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    List<MovieEntity> findByGenreName(String name);

    String genre(GenreEntity genre);
}
