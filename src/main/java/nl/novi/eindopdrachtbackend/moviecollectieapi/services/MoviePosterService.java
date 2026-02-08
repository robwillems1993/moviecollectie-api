package nl.novi.eindopdrachtbackend.moviecollectieapi.services;

import org.springframework.transaction.annotation.Transactional;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.poster.PosterDownloadDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.poster.PosterResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MovieEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.MoviePosterEntity;
import nl.novi.eindopdrachtbackend.moviecollectieapi.mappers.MoviePosterDTOMapper;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.MoviePosterRepository;
import nl.novi.eindopdrachtbackend.moviecollectieapi.repositories.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MoviePosterService {
    private final MovieRepository movieRepository;
    private final MoviePosterRepository moviePosterRepository;
    private final MoviePosterDTOMapper moviePosterDTOMapper;

    public MoviePosterService(MovieRepository movieRepository,
                              MoviePosterRepository moviePosterRepository,
                              MoviePosterDTOMapper moviePosterDTOMapper) {
        this.movieRepository = movieRepository;
        this.moviePosterRepository = moviePosterRepository;
        this.moviePosterDTOMapper = moviePosterDTOMapper;
    }

    @Transactional
    public PosterResponseDTO uploadPoster(Long movieId, MultipartFile file) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalStateException("Movie with id " + movieId + " not found"));

        MoviePosterEntity poster = moviePosterRepository.findByMovie_Id(movieId).orElse(null);
        if (poster == null) {
            poster = new MoviePosterEntity();
            poster.setMovie(movie);
            movie.setPoster(poster);
        }
        try {

            poster.setFileName(file.getOriginalFilename());
            poster.setContentType(file.getContentType());
            poster.setData(file.getBytes());
        } catch (IOException exception) {
            throw new IllegalStateException("Something went wrong while uploading");
        }
        poster = moviePosterRepository.save(poster);
        return moviePosterDTOMapper.mapToDto(poster);
    }

    @Transactional(readOnly = true)
    public PosterDownloadDTO downloadPoster(Long movieId) {
        MoviePosterEntity poster = moviePosterRepository.findByMovie_Id(movieId)
                .orElseThrow(()-> new IllegalStateException("Poster from movie with movie id " + movieId + " was not found"));

        return moviePosterDTOMapper.mapToDownloadDto(poster);
    }

    @Transactional
    public void deletePoster(Long movieId) {
        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(()-> new IllegalStateException("Movie from movie with movie id " + movieId + " was not found"));

        MoviePosterEntity poster = moviePosterRepository.findByMovie_Id(movieId)
                .orElseThrow(()-> new IllegalStateException("Poster from movie with movie id " + movieId + " was not found"));

        movie.setPoster(null);
        poster.setMovie(null);

        moviePosterRepository.delete(poster);
        moviePosterRepository.flush();
    }
}
