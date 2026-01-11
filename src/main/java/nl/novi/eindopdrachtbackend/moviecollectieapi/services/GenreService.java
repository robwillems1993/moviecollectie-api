package nl.novi.eindopdrachtbackend.moviecollectieapi.services;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.mappers.GenreDTOMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreDTOMapper genreDTOMapper;

    public GenreService(GenreRepository genreRepository, GenreDTOMapper genreDTOMapper) {
        this.genreRepository = genreRepository;
        this.genreDTOMapper = genreDTOMapper;
    }

    public List<GenreResponseDTO> findAllGenres() {
        List<GenreEntity> genreEntities = genreRepository.findAll();
        return genreDTOMapper.mapToDto(genreEntities);
    }

    public GenreResponseDTO findGenreById(Long id) {
        GenreEntity entity = genreRepository.findById(id).orElse(null);
        return entity == null ? null : genreDTOMapper.mapToDto(entity);
    }

    public GenreResponseDTO createGenre(GenreRequestDTO genreDTO) {
        GenreEntity entity = genreDTOMapper.mapToEntity(genreDTO);
        entity = genreRepository.save(entity);
        return genreDTOMapper.mapToDto(entity);
    }

    public GenreResponseDTO updateGenre(Long id, GenreRequestDTO genreDTO) {
        GenreEntity genreEntity = genreRepository.findById(id).orElse(null);
        if (genreEntity == null) {
            return null;
        }

        genreEntity.setName(genreDTO.getName());
        genreEntity.setDescription(genreDTO.getDescription());

        genreEntity = genreRepository.save(genreEntity);
        return genreDTOMapper.mapToDto(genreEntity);
    }

    public void deleteGenre(Long id) {
        GenreEntity genreEntity = genreRepository.findById(id).orElse(null);
        if (genreEntity == null) {
            return;
        }
        genreRepository.delete(genreEntity);
    }
}
