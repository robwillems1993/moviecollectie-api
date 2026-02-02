package nl.novi.eindopdrachtbackend.moviecollectieapi.services;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MovieEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.mappers.MovieDTOMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieDTOMapper movieDTOMapper;

    public MovieService(MovieRepository movieRepository, MovieDTOMapper movieDTOMapper) {
        this.movieRepository = movieRepository;
        this.movieDTOMapper = movieDTOMapper;
    }

    @Transactional(readOnly = true)
    public List<MovieResponseDTO> findAllMovies() {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        return movieDTOMapper.mapToDto(movieEntities);
    }

    @Transactional(readOnly = true)
    public MovieResponseDTO findMovieById(Long id) {
        MovieEntity entity = movieRepository.findById(id).orElseThrow(() -> new IllegalStateException("Movie with id " + id + " not found"));
        return movieDTOMapper.mapToDto(entity);
    }

    @Transactional
    public MovieResponseDTO createMovie(MovieRequestDTO movieDTO) {
        MovieEntity entity = movieDTOMapper.mapToEntity(movieDTO);
        entity = movieRepository.save(entity);
        return movieDTOMapper.mapToDto(entity);
    }

    @Transactional
    public MovieResponseDTO updateMovie(Long id, MovieRequestDTO movieDTO) {
        MovieEntity movieEntity = movieRepository.findById(id).orElseThrow(() -> new IllegalStateException("Movie with id " + id + " not found"));

        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setDirector(movieDTO.getDirector());
        movieEntity.setReleaseYear(movieDTO.getReleaseYear());
        movieEntity.setDescription(movieDTO.getDescription());

        movieEntity = movieRepository.save(movieEntity);
        return movieDTOMapper.mapToDto(movieEntity);
    }

    @Transactional
    public void deleteMovie(Long id) {
        MovieEntity movieEntity = movieRepository.findById(id).orElseThrow(() -> new IllegalStateException("Movie with id " + id + " not found"));
        movieRepository.delete(movieEntity);
    }
}
