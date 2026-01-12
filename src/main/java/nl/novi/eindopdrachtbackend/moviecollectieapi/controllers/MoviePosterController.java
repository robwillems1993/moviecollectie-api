package nl.novi.eindopdrachtbackend.moviecollectieapi.controllers;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.poster.PosterDownloadDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.poster.PosterResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.services.MoviePosterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/movies")
public class MoviePosterController {
    private final MoviePosterService moviePosterService;

    public MoviePosterController(MoviePosterService moviePosterService) {
        this.moviePosterService = moviePosterService;
    }

    @PostMapping("/{movieId}/poster")
    public ResponseEntity<PosterResponseDTO> uploadPoster(@PathVariable Long movieId, @RequestParam("file") MultipartFile file) throws IOException {
        PosterResponseDTO uploadedPoster = moviePosterService.uploadPoster(movieId, file);
        if (uploadedPoster == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedPoster);
    }

    @GetMapping("/{movieId}/poster")
    public ResponseEntity<byte[]> downloadPoster(@PathVariable Long movieId) {
        PosterDownloadDTO downloadedPoster = moviePosterService.downloadPoster(movieId);
        if (downloadedPoster == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(downloadedPoster.getData(), HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}/poster")
    public ResponseEntity<Void> deletePoster(@PathVariable Long movieId) {
        boolean deletedPoster = moviePosterService.deletePoster(movieId);
        if (!deletedPoster) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

