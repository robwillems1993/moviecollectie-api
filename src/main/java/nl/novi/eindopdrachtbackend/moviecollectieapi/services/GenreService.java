package nl.novi.eindopdrachtbackend.moviecollectieapi.services;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.mappers.GenreDTOMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreDTOMapper genreDTOMapper;

    public GenreService(GenreRepository genreRepository, GenreDTOMapper genreDTOMapper) {
        this.genreRepository = genreRepository;
        this.genreDTOMapper = genreDTOMapper;
    }

    @Transactional(readOnly = true)
    public List<GenreResponseDTO> findAllGenres() {
        List<GenreEntity> genreEntities = genreRepository.findAll();
        return genreDTOMapper.mapToDto(genreEntities);
    }

    @Transactional(readOnly = true)
    public GenreResponseDTO findGenreById(Long id) {
        GenreEntity entity = genreRepository.findById(id).orElseThrow(() -> new IllegalStateException("Genre with id " + id + " not found"));
        return genreDTOMapper.mapToDto(entity);
    }

    @Transactional
    public GenreResponseDTO createGenre(GenreRequestDTO genreDTO) {
        GenreEntity entity = genreDTOMapper.mapToEntity(genreDTO);
        entity = genreRepository.save(entity);
        return genreDTOMapper.mapToDto(entity);
    }

    @Transactional
    public GenreResponseDTO updateGenre(Long id, GenreRequestDTO genreDTO) {
        GenreEntity genreEntity = genreRepository.findById(id).orElseThrow(() -> new IllegalStateException("Genre with id " + id + " not found"));

        genreEntity.setName(genreDTO.getName());
        genreEntity.setDescription(genreDTO.getDescription());

        genreEntity = genreRepository.save(genreEntity);
        return genreDTOMapper.mapToDto(genreEntity);
    }

    @Transactional
    public void deleteGenre(Long id) {
        GenreEntity genreEntity = genreRepository.findById(id).orElseThrow(() -> new IllegalStateException("Genre with id " + id + " not found"));
        genreRepository.delete(genreEntity);
    }
}
