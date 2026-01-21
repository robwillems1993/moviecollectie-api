package nl.novi.eindopdrachtbackend.moviecollectieapi.mappers;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.collection.CollectionItemResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.CollectionItemEntity;
import org.springframework.stereotype.Component;

@Component
public class CollectionItemDTOMapper {
    public CollectionItemResponseDTO mapToDto(CollectionItemEntity entity) {
        CollectionItemResponseDTO dto = new CollectionItemResponseDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setMovieId(entity.getMovie().getId());
        dto.setMovieTitle(entity.getMovie().getTitle());
        dto.setStatus(entity.getStatus());
        dto.setFavorite(entity.isFavorite());
        dto.setRating(entity.getRating());
        return dto;
    }
}
