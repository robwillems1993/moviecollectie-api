package nl.novi.eindopdrachtbackend.moviecollectieapi.services;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MovieEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.exceptions.ConflictException;
import nl.novi.eindopdrachtbackend.moviecollectieapi.exceptions.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.moviecollectieapi.mappers.MovieDTOMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.CollectionItemRepository;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.GenreRepository;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieDTOMapper movieDTOMapper;
    private final GenreRepository genreRepository;
    private final CollectionItemRepository collectionItemRepository;

    public MovieService(MovieRepository movieRepository,
                        MovieDTOMapper movieDTOMapper,
                        GenreRepository genreRepository,
                        CollectionItemRepository collectionItemRepository) {
        this.movieRepository = movieRepository;
        this.movieDTOMapper = movieDTOMapper;
        this.genreRepository = genreRepository;
        this.collectionItemRepository = collectionItemRepository;
    }

    @Transactional(readOnly = true)
    public List<MovieResponseDTO> findAllMovies() {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        return movieDTOMapper.mapToDto(movieEntities);
    }

    @Transactional(readOnly = true)
    public List<MovieResponseDTO> findMoviesByGenreName(String genreName) {
        List<MovieEntity> movieEntities = movieRepository.findByGenreName(genreName);
        return movieDTOMapper.mapToDto(movieEntities);
    }

    @Transactional(readOnly = true)
    public MovieResponseDTO findMovieById(Long id) {
        MovieEntity entity = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie with id " + id + " not found"));
        return movieDTOMapper.mapToDto(entity);
    }

    @Transactional
    public MovieResponseDTO createMovie(MovieRequestDTO movieDTO) {
        boolean movieAlreadyExists = movieRepository.existsByTitleIgnoreCaseAndDirectorIgnoreCase(movieDTO.getTitle(), movieDTO.getDirector());

        if (movieAlreadyExists){
            throw new ConflictException("Movie already exists");
        }

        GenreEntity genre = genreRepository.findById(movieDTO.getGenreId()).orElseThrow(()-> new ResourceNotFoundException("Genre with id " + movieDTO.getGenreId() + " not found"));

        MovieEntity entity = movieDTOMapper.mapToEntity(movieDTO);
        entity.setGenre(genre);

        entity = movieRepository.save(entity);
        return movieDTOMapper.mapToDto(entity);
    }

    @Transactional
    public MovieResponseDTO updateMovie(Long id, MovieRequestDTO movieDTO) {
        MovieEntity movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie with id " + id + " not found"));

        boolean movieAlreadyExists = movieRepository.existsByTitleIgnoreCaseAndDirectorIgnoreCase(movieDTO.getTitle(), movieDTO.getDirector());

        boolean sameMovieAsExists =
                movie.getTitle().equalsIgnoreCase(movieDTO.getTitle()) &&
                movie.getDirector().equalsIgnoreCase(movieDTO.getDirector());

        if (movieAlreadyExists && !sameMovieAsExists) {
            throw new ConflictException("Movie already exissts");        }

        GenreEntity genre = genreRepository.findById(movieDTO.getGenreId()).orElseThrow(() -> new ResourceNotFoundException("Genre with id " + movieDTO.getGenreId() + " not found"));

        movie.setTitle(movieDTO.getTitle());
        movie.setDirector(movieDTO.getDirector());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setDescription(movieDTO.getDescription());
        movie.setGenre(genre);

        movie = movieRepository.save(movie);
        return movieDTOMapper.mapToDto(movie);
    }

    @Transactional
    public void deleteMovie(Long id) {
        MovieEntity movieEntity = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie with id " + id + " not found"));

        collectionItemRepository.deleteAllCollectionsByMovieId(id);
        movieRepository.delete(movieEntity);
    }
}
