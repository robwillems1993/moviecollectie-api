package nl.novi.eindopdrachtbackend.moviecollectieapi.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreRequestDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class GenreControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GenreRepository genreRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        genreRepository.deleteAll();

        GenreEntity genre = new GenreEntity();
        genre.setName("Action");
        genre.setDescription("Action");
        genreRepository.save(genre);
    }

    @Test
    void getGenreById_whenGenreCantBeFound() throws Exception {
        mockMvc.perform(get("/genres/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createGenre_canCreateAndReturnGenre() throws Exception {
        // Arrange
        GenreRequestDTO dto = new GenreRequestDTO();
        dto.setName("War");
        dto.setDescription("War description");

        // Act en Assert
        mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("War"))
                .andExpect(jsonPath("$.description").value("War description"));
    }
}