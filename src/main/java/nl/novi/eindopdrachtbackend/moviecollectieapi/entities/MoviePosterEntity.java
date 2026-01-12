package nl.novi.eindopdrachtbackend.moviecollectieapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "movieposters")
public class MoviePosterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String contentType;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "movie_id", nullable = false, unique = true)
    private MovieEntity movie;

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }
}
