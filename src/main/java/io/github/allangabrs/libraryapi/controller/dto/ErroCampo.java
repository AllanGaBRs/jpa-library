package io.github.allangabrs.libraryapi.controller.dto;

public record ErroCampo(
        String field,
        String error
) {
}
