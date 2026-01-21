package nl.novi.eindopdrachtbackend.moviecollectieapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "collection_items", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "movie_id"}))
public class CollectionItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.TO_WATCH;

    @Column(nullable = false)
    private boolean favorite = false;

    private Integer rating;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public enum Status {
        TO_WATCH,
        WATCHED
    }
}
