package nl.novi.eindopdrachtbackend.moviecollectieapi.servicesTests;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.mappers.GenreDTOMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.GenreRepository;
import nl.novi.eindopdrachtbackend.moviecollectieapi.services.GenreService;
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
class GenreServiceTest {

    @Mock GenreRepository genreRepository;
    @Mock GenreDTOMapper genreDTOMapper;

    @InjectMocks
    GenreService genreService;

    @Test
    void findAllGenresTest() {
        // Arrange
        List<GenreEntity> entities = List.of(new GenreEntity());
        List<GenreResponseDTO> dtos = List.of(new GenreResponseDTO());

        when(genreRepository.findAll()).thenReturn(entities);
        when(genreDTOMapper.mapToDto(entities)).thenReturn(dtos);

        // Act
        List<GenreResponseDTO> result = genreService.findAllGenres();

        // Assert
        assertSame(dtos, result);
        verify(genreRepository, times(1)).findAll();
        verify(genreDTOMapper, times(1)).mapToDto(entities);
    }

    @Test
    void findGenreById_empty_returnException() {
        // Arrange
        Long id = 1L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        // Act en Assert
        assertThrows(IllegalStateException.class,() -> genreService.findGenreById(id));
    }

    @Test
    void findGenreById_result_returnGenre() {
        // Arrange
        Long id = 2L;

        GenreEntity entity = new GenreEntity();
        GenreResponseDTO dto = new GenreResponseDTO();

        when(genreRepository.findById(id)).thenReturn(Optional.of(entity));
        when(genreDTOMapper.mapToDto(entity)).thenReturn(dto);

        // Act
        GenreResponseDTO result = genreService.findGenreById(id);

        // Assert
        assertSame(dto, result);
        verify(genreRepository).findById(id);
        verify(genreDTOMapper).mapToDto(entity);
    }

    @Test
    void createGenreTest() {
        // Arrange
        GenreRequestDTO request = new GenreRequestDTO();
        request.setName("Action");
        request.setDescription("Do you have anything without Jason Statham?");

        GenreEntity entityToSave = new GenreEntity();
        GenreEntity savedEntity = new GenreEntity();
        GenreResponseDTO response = new GenreResponseDTO();

        when(genreDTOMapper.mapToEntity(request)).thenReturn(entityToSave);
        when(genreRepository.save(entityToSave)).thenReturn(savedEntity);
        when(genreDTOMapper.mapToDto(savedEntity)).thenReturn(response);

        // Act
        GenreResponseDTO result = genreService.createGenre(request);

        // Assert
        assertSame(response, result);
        verify(genreDTOMapper).mapToEntity(request);
        verify(genreRepository).save(entityToSave);
        verify(genreDTOMapper).mapToDto(savedEntity);
    }

    @Test
    void updateGenre_empty_returnException() {
        // Arrange
        Long id = 3L;

        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        // Act en Assert
        assertThrows(IllegalStateException.class,()-> genreService.updateGenre(id, new GenreRequestDTO()));
    }

    @Test
    void updateGenre_whenFound_updateGenre() {
        // Arrange
        Long id = 4L;
        GenreRequestDTO request = new GenreRequestDTO();
        request.setName("Star Trek");
        request.setDescription("No one really likes Star Trek");

        GenreEntity existing = new GenreEntity();
        GenreResponseDTO response = new GenreResponseDTO();

        when(genreRepository.findById(id)).thenReturn(Optional.of(existing));
        when(genreRepository.save(existing)).thenReturn(existing);
        when(genreDTOMapper.mapToDto(existing)).thenReturn(response);

        // Act
        GenreResponseDTO result = genreService.updateGenre(id, request);

        // Assert
        assertSame(response, result);
        assertEquals("Star Trek", existing.getName());
        assertEquals("No one really likes Star Trek", existing.getDescription());

        verify(genreRepository).findById(id);
        verify(genreRepository).save(existing);
        verify(genreDTOMapper).mapToDto(existing);
    }

    @Test
    void deleteGenreTest_whenNotFound_returnException() {
        // Arrange
        Long id = 5L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        // Act en Assert
        assertThrows(IllegalStateException.class, ()-> genreService.deleteGenre(id));
    }

    @Test
    void deleteGenreTest_whenFound_deleteGenre() {
        // Arrange
        Long id = 6L;
        GenreEntity entity = new GenreEntity();
        when(genreRepository.findById(id)).thenReturn(Optional.of(entity));

        // Act
        genreService.deleteGenre(id);

        // Assert
        verify(genreRepository).delete(entity);
    }
}
