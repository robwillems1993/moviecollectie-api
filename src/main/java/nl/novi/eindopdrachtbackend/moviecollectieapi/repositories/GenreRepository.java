package nl.novi.eindopdrachtbackend.moviecollectieapi.repositories;

import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}
