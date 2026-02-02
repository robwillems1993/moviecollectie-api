package nl.novi.eindopdrachtbackend.moviecollectieapi.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.collection.CollectionItemResponseDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.dtos.collection.CollectionItemUpdateDTO;
import nl.novi.eindopdrachtbackend.moviecollectieapi.services.CollectionItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CollectionItemController {
    private final CollectionItemService collectionItemService;

    public CollectionItemController(CollectionItemService collectionService) {
        this.collectionItemService = collectionService;
    }

    @PostMapping("/collections/{movieId}")
    public ResponseEntity<CollectionItemResponseDTO> addToMyWatchlist(@PathVariable Long movieId,
                                                                      Authentication authentication) {
        String username = authentication.getName();
        try {
            CollectionItemResponseDTO created = collectionItemService.addMovieToCollection(username, movieId);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/collections")
    public ResponseEntity<List<CollectionItemResponseDTO>> getMyWatchlist(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(collectionItemService.getMyCollection(username));
    }

    @PutMapping("/collections/{movieId}")
    public ResponseEntity<CollectionItemResponseDTO> updateMyWatchlist(@PathVariable Long movieId,
                                                                       @Valid @RequestBody CollectionItemUpdateDTO dto,
                                                                       Authentication authentication) {
        String username = authentication.getName();
        try {
            return ResponseEntity.ok(collectionItemService.updateMyCollectionItem(username, movieId, dto));
        } catch (IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/collections/{movieId}")
    public ResponseEntity<Void> deleteWatchlistMovie(@PathVariable Long movieId,
                                                     Authentication authentication) {
        String username = authentication.getName();
        try {
            collectionItemService.deleteMyCollectionItem(username, movieId);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
