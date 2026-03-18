package nl.novi.eindopdrachtbackend.moviecollectieapi.exceptions;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}