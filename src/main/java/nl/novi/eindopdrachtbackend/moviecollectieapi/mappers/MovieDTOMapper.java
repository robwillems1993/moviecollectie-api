package nl.novi.eindopdrachtbackend.moviecollectieapi.mappers;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MovieEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieDTOMapper {
    public MovieResponseDTO mapToDto(MovieEntity entity) {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDirector(entity.getDirector());
        dto.setReleaseYear(entity.getReleaseYear());
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    public List<MovieResponseDTO> mapToDto(List<MovieEntity> entities) {
        List<MovieResponseDTO> dtos = new ArrayList<>();
        for (MovieEntity entity : entities) {
            dtos.add(mapToDto(entity));
        }
        return dtos;
    }

    public MovieEntity mapToEntity(MovieRequestDTO dto) {
        MovieEntity entity = new MovieEntity();
        entity.setTitle(dto.getTitle());
        entity.setDirector(dto.getDirector());
        entity.setReleaseYear(dto.getReleaseYear());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
