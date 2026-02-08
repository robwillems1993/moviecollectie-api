package nl.novi.eindopdrachtbackend.moviecollectieapi.servicesTests;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MovieEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.mappers.MovieDTOMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.GenreRepository;
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
    @Mock GenreRepository genreRepository;

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
    }

    @Test
    void findMoviesByGenreNameTest() {
        // Arrange
        String genreName = "Action";
        List<MovieEntity> movies = List.of(new MovieEntity());
        List<MovieResponseDTO> dtos = List.of(new MovieResponseDTO());

        when(movieRepository.findByGenreName(genreName)).thenReturn(movies);
        when(movieDTOMapper.mapToDto(movies)).thenReturn(dtos);

        // Act
        List<MovieResponseDTO> result = movieService.findMoviesByGenreName(genreName);

        // Assert
        assertSame(dtos, result);
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
        newMovie.setGenreId(1L);

        GenreEntity genre = new GenreEntity();

        MovieEntity entityToSave = new MovieEntity();
        MovieEntity savedEntity = new MovieEntity();
        MovieResponseDTO response = new MovieResponseDTO();

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(movieDTOMapper.mapToEntity(newMovie)).thenReturn(entityToSave);
        when(movieRepository.save(entityToSave)).thenReturn(savedEntity);
        when(movieDTOMapper.mapToDto(savedEntity)).thenReturn(response);

        // Act
        MovieResponseDTO result = movieService.createMovie(newMovie);

        // Assert
        assertSame(response, result);
    }

    @Test
    void createMovie_GenreNotFound_ReturnException(){
        // Arrange
        MovieRequestDTO request = new MovieRequestDTO();
        request.setGenreId(2L);

        when(genreRepository.findById(2L)).thenReturn(Optional.empty());

        // Act en Assert
        assertThrows(IllegalStateException.class, () -> movieService.createMovie(request));
    }

    @Test
    void updateMovie_emptyMovie_returnException() {
        // Arrange
        Long id = 5L;
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        // Act en Assert
        assertThrows(IllegalStateException.class,()-> movieService.updateMovie(id, new MovieRequestDTO()));
    }

    @Test
    void updateMovie_emptyGenre_returnException() {
        // Arrange
        Long movieId = 6L;
        MovieEntity movie = new MovieEntity();
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        MovieRequestDTO request = new MovieRequestDTO();
        request.setGenreId(3L);

        when(genreRepository.findById(3L)).thenReturn(Optional.empty());

        // Act en Assert
        assertThrows(IllegalStateException.class, ()-> movieService.updateMovie(movieId,request));
    }

    @Test
    void updateMovie_whenFound_updateMovie() {
        // Arrange
        Long id = 7L;

        MovieRequestDTO foundMovie = new MovieRequestDTO();
        foundMovie.setTitle("Predestination");
        foundMovie.setDirector("Ali Babba");
        foundMovie.setReleaseYear(2014);
        foundMovie.setDescription("A temporal agent is sent on an intricate series of time-travel journeys designed to ensure the continuation of his law enforcement career for eternity.");
        foundMovie.setGenreId(4L);

        MovieEntity existing = new MovieEntity();
        GenreEntity genre = new GenreEntity();

        MovieResponseDTO response = new MovieResponseDTO();

        when(movieRepository.findById(id)).thenReturn(Optional.of(existing));
        when(genreRepository.findById(4L)).thenReturn(Optional.of(genre));
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
        assertEquals(genre, existing.getGenre());
    }

    @Test
    void deleteMovieTest_ifEmpty_returnException() {
        // Arrange
        Long id = 8L;
        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        // Act en Assert
        assertThrows(IllegalStateException.class, ()-> movieService.deleteMovie(id));
    }

    @Test
    void deleteMovieTest_whenFound_deleteMovie() {
        // Arrange
        Long id = 9L;
        MovieEntity entity = new MovieEntity();
        when(movieRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        movieService.deleteMovie(id);

        // Assert
        verify(movieRepository).delete(entity);
    }

}
