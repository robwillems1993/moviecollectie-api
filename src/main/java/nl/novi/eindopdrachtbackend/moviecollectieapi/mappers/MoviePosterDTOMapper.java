package nl.novi.eindopdrachtbackend.moviecollectieapi.mappers;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.poster.PosterDownloadDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.poster.PosterResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MoviePosterEntity;
import org.springframework.stereotype.Component;

@Component
public class MoviePosterDTOMapper {
    public PosterResponseDTO mapToDto(MoviePosterEntity entity) {
        PosterResponseDTO dto = new PosterResponseDTO();
        dto.setId(entity.getId());
        dto.setMovieId(entity.getMovie().getId());
        dto.setFileName(entity.getFileName());
        dto.setContentType(entity.getContentType());
        return dto;
    }

    public PosterDownloadDTO mapToDownloadDto(MoviePosterEntity entity) {
        PosterDownloadDTO dto = new PosterDownloadDTO();
        dto.setFileName(entity.getFileName());
        dto.setContentType(entity.getContentType());
        dto.setData(entity.getData());
        return dto;
    }
}
