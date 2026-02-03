package nl.novi.eindopdrachtbackend.moviecollectieapi.servicesTests;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MovieEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.mappers.MovieDTOMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.MovieRepository;
import nl.novi.eindopdrachtbackend.moviecollectieapi.services.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock MovieRepository movieRepository;
    @Mock MovieDTOMapper movieDTOMapper;

    @InjectMocks
    MovieService movieService;

    @Test
    void findAllMoviesTest() {
        // Arrange
        List<MovieEntity> entities = List.of(new MovieEntity());
        List<MovieResponseDTO> dtos = List.of(new MovieResponseDTO());

        when(movieRepository.findAll()).thenReturn(entities);
        when(movieDTOMapper.mapToDto(entities)).thenReturn(dtos);

        // Act
        List<MovieResponseDTO> result = movieService.findAllMovies();

        // Assert
        assertSame(dtos, result);
        verify(movieRepository).findAll();
        verify(movieDTOMapper).mapToDto(entities);
    }

    @Test
    void findMovieById_ifEmpty_returnException() {
        // Arrange
        Long id = 1L;
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        // Act en Assert
        assertThrows(IllegalStateException.class,()-> movieService.findMovieById(id));
    }

    @Test
    void findMovieById_ifFound_returnMovie() {
        // Arrange
        Long id = 2L;
        MovieEntity entity = new MovieEntity();
        MovieResponseDTO dto = new MovieResponseDTO();

        when(movieRepository.findById(id)).thenReturn(Optional.of(entity));
        when(movieDTOMapper.mapToDto(entity)).thenReturn(dto);

        // Act
        MovieResponseDTO result = movieService.findMovieById(id);

        // Assert
        assertSame(dto, result);
    }


    @Test
    void createMovieTest() {
        // Arrange
        MovieRequestDTO newMovie = new MovieRequestDTO();
        newMovie.setTitle("Predestination");
        newMovie.setDirector("Michael Spierig");
        newMovie.setReleaseYear(2014);
        newMovie.setDescription("A temporal agent is sent on an intricate series of time-travel journeys designed to ensure the continuation of his law enforcement career for eternity.");

        MovieEntity entityToSave = new MovieEntity();
        MovieEntity savedEntity = new MovieEntity();
        MovieResponseDTO response = new MovieResponseDTO();

        when(movieDTOMapper.mapToEntity(newMovie)).thenReturn(entityToSave);
        when(movieRepository.save(entityToSave)).thenReturn(savedEntity);
        when(movieDTOMapper.mapToDto(savedEntity)).thenReturn(response);

        // Act
        MovieResponseDTO result = movieService.createMovie(newMovie);

        // Assert
        assertSame(response, result);
        verify(movieDTOMapper).mapToEntity(newMovie);
        verify(movieRepository).save(entityToSave);
        verify(movieDTOMapper).mapToDto(savedEntity);
    }

    @Test
    void updateMovie_empty_returnException() {
        // Arrange
        Long id = 3L;
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        // Act en Assert
        assertThrows(IllegalStateException.class,()-> movieService.updateMovie(id, new MovieRequestDTO()));
    }

    @Test
    void updateMovie_whenFound_updateMovie() {
        // Arrange
        Long id = 4L;

        MovieRequestDTO foundMovie = new MovieRequestDTO();
        foundMovie.setTitle("Predestination");
        foundMovie.setDirector("Ali Babba");
        foundMovie.setReleaseYear(2014);
        foundMovie.setDescription("A temporal agent is sent on an intricate series of time-travel journeys designed to ensure the continuation of his law enforcement career for eternity.");

        MovieEntity existing = new MovieEntity();
        MovieResponseDTO response = new MovieResponseDTO();

        when(movieRepository.findById(id)).thenReturn(Optional.of(existing));
        when(movieRepository.save(existing)).thenReturn(existing);
        when(movieDTOMapper.mapToDto(existing)).thenReturn(response);

        // Act
        MovieResponseDTO result = movieService.updateMovie(id, foundMovie);

        // Assert
        assertSame(response, result);
        assertEquals("Predestination", existing.getTitle());
        assertEquals("Ali Babba", existing.getDirector());
        assertEquals(2014, existing.getReleaseYear());
        assertEquals("A temporal agent is sent on an intricate series of time-travel journeys designed to ensure the continuation of his law enforcement career for eternity.", existing.getDescription());

        verify(movieRepository).findById(id);
        verify(movieRepository).save(existing);
        verify(movieDTOMapper).mapToDto(existing);
    }

    @Test
    void deleteMovieTest_ifEmpty_returnException() {
        // Arrange
        Long id = 5L;
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        // Act en Assert
        assertThrows(IllegalStateException.class, ()-> movieService.deleteMovie(id));
    }

    @Test
    void deleteMovieTest_whenFound_deleteMovie() {
        // Arrange
        Long id = 6L;
        MovieEntity entity = new MovieEntity();
        when(movieRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        movieService.deleteMovie(id);

        // Assert
        verify(movieRepository).delete(entity);
    }

}
