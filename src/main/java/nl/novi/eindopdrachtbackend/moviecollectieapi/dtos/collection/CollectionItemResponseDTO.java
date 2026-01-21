package nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.collection;

import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.CollectionItemEntity;

public class CollectionItemResponseDTO {
    private Long id;
    private String username;
    private Long movieId;
    private String movieTitle;
    private CollectionItemEntity.Status status;
    private boolean favorite;
    private Integer rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public CollectionItemEntity.Status getStatus() {
        return status;
    }

    public void setStatus(CollectionItemEntity.Status status) {
        this.status = status;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
