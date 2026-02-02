package nl.novi.eindopdrachtbackend.moviecollectieapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre.GenreResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.services.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres() {
        List<GenreResponseDTO> genres = genreService.findAllGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> getGenreById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(genreService.findGenreById(id));
        } catch (IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GenreResponseDTO> createGenre(@Valid @RequestBody GenreRequestDTO genreDTO) {
        GenreResponseDTO createdGenre = genreService.createGenre(genreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponseDTO> updateGenre(@PathVariable Long id, @Valid @RequestBody GenreRequestDTO genreDTO) {
        try {
            return ResponseEntity.ok(genreService.updateGenre(id, genreDTO));
        } catch (IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        try {
            genreService.deleteGenre(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
