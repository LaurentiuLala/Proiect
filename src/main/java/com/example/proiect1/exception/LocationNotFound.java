package com.example.proiect1.exception;

public class LocationNotFound extends RuntimeException {
    public LocationNotFound(String message) {
        super(message);
    }
}
