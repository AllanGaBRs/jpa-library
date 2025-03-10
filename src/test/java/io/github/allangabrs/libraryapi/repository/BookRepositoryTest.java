package io.github.allangabrs.libraryapi.repository;

import io.github.allangabrs.libraryapi.model.Author;
import io.github.allangabrs.libraryapi.model.Book;
import io.github.allangabrs.libraryapi.model.BookGender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void saveTest() {
        Book book = new Book();
        book.setIsbn("90887-54854");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(BookGender.FICCAO);
        book.setTitle("OVNIs");
        book.setPublicationDate(LocalDate.of(2000, 10, 20));

        Author author = authorRepository
                .findById(UUID.fromString("33fe0b68-3883-416d-9244-359f5f794589"))
                .orElse(null);

        book.setAuthor(author);
        bookRepository.save(book);
    }

}