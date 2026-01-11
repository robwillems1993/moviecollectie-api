package nl.novi.eindopdrachtbackend.moviecollectieapi.mappers;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.GenreEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreDTOMapper {

    public GenreResponseDTO mapToDto(GenreEntity entity) {
        GenreResponseDTO dto = new GenreResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public List<GenreResponseDTO> mapToDto(List<GenreEntity> entities) {
        List<GenreResponseDTO> dtos = new ArrayList<>();
        for (GenreEntity entity : entities) {
            dtos.add(mapToDto(entity));
        }
        return dtos;
    }

    public GenreEntity mapToEntity(GenreRequestDTO dto) {
        GenreEntity entity = new GenreEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
