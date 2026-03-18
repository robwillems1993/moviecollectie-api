package nl.novi.eindopdrachtbackend.moviecollectieapi.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.GenreEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class MovieControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GenreRepository genreRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private GenreEntity genre;

    @BeforeEach
    void setUp() {
        genreRepository.deleteAll();

        genre = new GenreEntity();
        genre.setName("Action");
        genre.setDescription("Action description");
        genreRepository.save(genre);
    }

    @Test
    void getMovieById_whenMovieCantBeFound() throws Exception {
        mockMvc.perform(get("/movies/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createMovie_canCreateMovie() throws Exception {
        // Arrange
        MovieRequestDTO dto = new MovieRequestDTO();
        dto.setTitle("The Dark Knight");
        dto.setDirector("Christopher Nolan");
        dto.setReleaseYear(2008);
        dto.setDescription("Batman movie");
        dto.setGenreId(genre.getId());

        // Act en Assert
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("The Dark Knight"))
                .andExpect(jsonPath("$.director").value("Christopher Nolan"))
                .andExpect(jsonPath("$.releaseYear").value(2008));
    }
}