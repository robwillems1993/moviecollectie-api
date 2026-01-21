package nl.novi.eindopdrachtbackend.moviecollectieapi.repositories;

import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.CollectionItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionItemRepository extends JpaRepository<CollectionItemEntity, Long> {
    boolean existsByUsernameAndMovieId(String username, Long movieId);

    List<CollectionItemEntity> findAllByUsername(String username);

    Optional<CollectionItemEntity> findByUsernameAndMovieId(String username, Long movieId);
}
