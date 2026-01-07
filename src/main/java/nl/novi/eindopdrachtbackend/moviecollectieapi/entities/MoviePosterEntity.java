package nl.novi.eindopdrachtbackend.moviecollectieapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "movieposters")
public class MoviePosterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
}
