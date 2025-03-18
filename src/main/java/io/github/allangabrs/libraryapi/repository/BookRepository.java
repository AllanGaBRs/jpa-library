package io.github.allangabrs.libraryapi.repository;

import io.github.allangabrs.libraryapi.model.Author;
import io.github.allangabrs.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByAuthor(Author author);

}
