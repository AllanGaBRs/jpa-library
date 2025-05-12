package io.github.allangabrs.libraryapi.controller.dto;

import io.github.allangabrs.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatorio")
        String name,
        @NotNull(message = "campo obrigatorio")
        LocalDate dateBirth,
        @NotBlank(message = "campo obrigatorio")
        String nationality
) {
    public Author entity(){
        Author author = new Author();
        author.setName(this.name);
        author.setDateBirth(this.dateBirth);
        author.setNationality(this.nationality);
        return author;
    }
}
