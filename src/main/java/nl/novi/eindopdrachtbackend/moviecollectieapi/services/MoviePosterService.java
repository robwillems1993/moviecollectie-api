package nl.novi.eindopdrachtbackend.moviecollectieapi.services;

import org.springframework.transaction.annotation.Transactional;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.poster.PosterDownloadDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.poster.PosterRequestDTO;
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

    public PosterResponseDTO uploadPoster(Long movieId, MultipartFile file) throws IOException {
        MovieEntity movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return null;
        }

        MoviePosterEntity poster = moviePosterRepository.findByMovieId(movieId);
        if (poster == null) {
            poster = new MoviePosterEntity();
            poster.setMovie(movie);
        }

        poster.setFileName(file.getOriginalFilename());
        poster.setContentType(file.getContentType());
        poster.setData(file.getBytes());

        poster = moviePosterRepository.save(poster);
        return moviePosterDTOMapper.mapToDto(poster);
    }

    @Transactional(readOnly = true)
    public PosterDownloadDTO downloadPoster(Long movieId) {
        MoviePosterEntity poster = moviePosterRepository.findByMovieId(movieId);
        if (poster == null) {
            return null;
        }
        return moviePosterDTOMapper.mapToDownloadDto(poster);
    }

    @Transactional(readOnly = true)
    public boolean deletePoster(Long movieId) {
        MoviePosterEntity poster = moviePosterRepository.findByMovieId(movieId);
        if (poster == null) {
            return false;
        }
        moviePosterRepository.delete(poster);
        return true;
    }
}
