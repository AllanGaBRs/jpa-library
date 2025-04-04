package io.github.allangabrs.libraryapi.controller.dto;

import io.github.allangabrs.libraryapi.model.Author;

import java.time.LocalDate;

public record AuthorDTO(
        String name,
        LocalDate dateBirth,
        String nationality
) {
    public Author entity(){
        Author author = new Author();
        author.setName(this.name);
        author.setDate_birth(this.dateBirth);
        author.setNationality(this.nationality);
        return author;
    }
}
