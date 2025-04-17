package io.github.allangabrs.libraryapi.excecptions;

public class OperationNotPermittedException extends RuntimeException{
    public OperationNotPermittedException(String message) {
        super(message);
    }
}
