package nl.novi.eindopdrachtbackend.moviecollectieapi.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private Integer releaseYear;
    private String description;
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "genre_id", nullable = false)
    private GenreEntity genre;

    @OneToOne(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private MoviePosterEntity poster;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public MoviePosterEntity getPoster() {
        return poster;
    }

    public void setPoster(MoviePosterEntity poster) {
        this.poster = poster;
    }

    public GenreEntity getGenre() {
        return genre;
    }

    public void setGenre(GenreEntity genre) {
        this.genre = genre;
    }
}
