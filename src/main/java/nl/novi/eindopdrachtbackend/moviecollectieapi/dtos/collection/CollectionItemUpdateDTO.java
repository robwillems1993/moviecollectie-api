package nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.collection;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import nl.novi.eindopdrachtbackend.moviecollectieapi.entities.CollectionItemEntity;

public class CollectionItemUpdateDTO {
    private CollectionItemEntity.Status status;
    private Boolean favorite;

    @Min(1)
    @Max(5)
    private Integer rating;

    public CollectionItemEntity.Status getStatus() {
        return status;
    }

    public void setStatus(CollectionItemEntity.Status status) {
        this.status = status;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
