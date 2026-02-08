package nl.novi.eindopdrachtbackend.moviecollectieapi.controllers;

import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.poster.PosterDownloadDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.poster.PosterResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.services.MoviePosterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/movies")
public class MoviePosterController {
    private final MoviePosterService moviePosterService;

    public MoviePosterController(MoviePosterService moviePosterService) {
        this.moviePosterService = moviePosterService;
    }

    @PostMapping("/{movieId}/poster")
    public ResponseEntity<PosterResponseDTO> uploadPoster(@PathVariable Long movieId, @RequestParam("file") MultipartFile file) {
        try {
            PosterResponseDTO uploadedPoster = moviePosterService.uploadPoster(movieId, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadedPoster);
        } catch (IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{movieId}/poster")
    public ResponseEntity<byte[]> downloadPoster(@PathVariable Long movieId) {
        try {
            PosterDownloadDTO downloadedPoster = moviePosterService.downloadPoster(movieId);


            return ResponseEntity.ok()
                    .header("Content-Type", downloadedPoster.getContentType())
                    .header("Content-Disposition", "inline; filename=\"" + downloadedPoster.getFileName() + "\"")
                    .body(downloadedPoster.getData());
        } catch (IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{movieId}/poster")
    public ResponseEntity<Void> deletePoster(@PathVariable Long movieId) {
        try {
            moviePosterService.deletePoster(movieId);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}

