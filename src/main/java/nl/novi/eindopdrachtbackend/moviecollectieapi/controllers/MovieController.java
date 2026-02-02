package nl.novi.eindopdrachtbackend.moviecollectieapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieRequestDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.movie.MovieResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies() {
        List<MovieResponseDTO> movies = movieService.findAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getMovieById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movieService.findMovieById(id));
        } catch (IllegalStateException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<MovieResponseDTO> createMovie(@Valid @RequestBody MovieRequestDTO movieDTO) {
        MovieResponseDTO createdMovie = movieService.createMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieRequestDTO movieDTO) {
        try {
            return ResponseEntity.ok(movieService.updateMovie(id, movieDTO));
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        try {
            movieService.deleteMovie(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
