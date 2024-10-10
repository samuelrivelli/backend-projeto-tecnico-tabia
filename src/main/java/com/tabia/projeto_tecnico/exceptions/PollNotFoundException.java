package com.tabia.projeto_tecnico.exceptions;

public class PollNotFoundException extends RuntimeException {
    public PollNotFoundException(String message) {
        super(message);
    }
}
