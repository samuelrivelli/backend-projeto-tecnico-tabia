package com.tabia.projeto_tecnico.exceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String msg){
        super(msg);
    }
}
