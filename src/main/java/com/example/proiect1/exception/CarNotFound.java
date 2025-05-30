package com.example.proiect1.exception;

public class CarNotFound extends RuntimeException {
    public CarNotFound(String message) {
        super(message);
    }
}
