package nl.novi.eindopdrachtbackend.moviecollectieapi.services;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.collection.CollectionItemResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.collection.CollectionItemUpdateDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.CollectionItemEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MovieEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.mappers.CollectionItemDTOMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.CollectionItemRepository;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionItemService {
    private final CollectionItemRepository collectionItemRepository;
    private final MovieRepository movieRepository;
    private final CollectionItemDTOMapper mapper;

    public CollectionItemService(CollectionItemRepository collectionItemRepository,
                             MovieRepository movieRepository,
                             CollectionItemDTOMapper mapper) {
        this.collectionItemRepository = collectionItemRepository;
        this.movieRepository = movieRepository;
        this.mapper = mapper;
    }

    @Transactional
    public CollectionItemResponseDTO addMovieToCollection(String username, Long movieId) {
        if (collectionItemRepository.existsByUsernameAndMovieId(username, movieId)) {
            throw new IllegalStateException("Movie already in collection");
        }

        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalStateException("Movie not found"));

        CollectionItemEntity item = new CollectionItemEntity();
        item.setUsername(username);
        item.setMovie(movie);
        item.setStatus(CollectionItemEntity.Status.TO_WATCH);
        item.setFavorite(false);
        item.setRating(null);

        CollectionItemEntity saved = collectionItemRepository.save(item);
        return mapper.mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<CollectionItemResponseDTO> getMyCollection(String username) {
        List<CollectionItemEntity> entities = collectionItemRepository.findAllByUsername(username);
        List<CollectionItemResponseDTO> dtos = new ArrayList<>();
        for (CollectionItemEntity entity : entities) {
            dtos.add(mapper.mapToDto(entity));
        }
        return dtos;
    }

    @Transactional
    public CollectionItemResponseDTO updateMyCollectionItem(String username, Long movieId, CollectionItemUpdateDTO dto) {
        CollectionItemEntity item = collectionItemRepository.findByUsernameAndMovieId(username, movieId)
                .orElseThrow(() -> new IllegalStateException("Collection item not found"));

        if (dto.getStatus() != null) {
            item.setStatus(dto.getStatus());
        }
        if (dto.getFavorite() != null) {
            item.setFavorite(dto.getFavorite());
        }
        if (dto.getRating() != null) {
            item.setRating(dto.getRating());
        }

        CollectionItemEntity saved = collectionItemRepository.save(item);
        return mapper.mapToDto(saved);
    }

    @Transactional
    public void deleteMyCollectionItem(String username, Long movieId) {
        CollectionItemEntity item = collectionItemRepository.findByUsernameAndMovieId(username, movieId)
                .orElseThrow(() -> new IllegalStateException("Collection item not found"));
        collectionItemRepository.delete(item);
    }
}
