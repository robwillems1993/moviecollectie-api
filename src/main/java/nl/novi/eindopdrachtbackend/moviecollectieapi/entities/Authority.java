package nl.novi.eindopdrachtbackend.moviecollectieapi.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Entity
@Table(name = "authorities")
public class Authority implements Serializable {
    @Id
    @Column(nullable = false)
    private String username;
    @Id
    @Column(nullable = false)
    private String authority;
    // getters and setters ...
}