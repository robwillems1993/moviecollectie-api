package nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.genre;

import jakarta.validation.constraints.NotBlank;

public class GenreRequestDTO {
    @NotBlank
    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
