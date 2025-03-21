package io.github.allangabrs.libraryapi.repository;

import io.github.allangabrs.libraryapi.model.Author;
import io.github.allangabrs.libraryapi.model.Book;
import io.github.allangabrs.libraryapi.model.BookGender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

    @Autowired
    BookRepository bookRepository;

    @Test
    void saveTest(){
        Author author = new Author();
        author.setName("allan");
        author.setNationality("ingles");
        author.setDate_birth(LocalDate.now());

        author = repository.save(author);
        System.out.println("author " + author);
    }

    @Test
    void updateTest(){
        var id = UUID.fromString("eeb3d705-c280-4597-aa2f-e8fac7dc24b6");

        Optional<Author> possibleAuthor = repository.findById(id);

        if(possibleAuthor.isPresent()){
            Author author = possibleAuthor.get();

            System.out.println("Dados do author");
            System.out.println(author);

            author.setName("Allan");
            author.setDate_birth(LocalDate.of(2020, 10,10));
            repository.save(author);
        }
    }

    @Test
    void listTest(){
        List<Author> list = repository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    void countTest(){
        System.out.println("Contagem de authores: " + repository.count());
    }

    @Test
    void deleteByIdTest(){
        var id = UUID.fromString("0d2acd9e-ea0f-4f09-841e-2f10de2f6d2c");
        repository.deleteById(id);
    }

    @Test
    void deleteTest(){
        var id = UUID.fromString("5554ee52-94d3-45a0-a5fb-96e75ff9390a");
        var author = repository.findById(id).get();
        repository.delete(author);
    }

    @Test
    void saveAuthorWithBook(){
        Author author = new Author();
        author.setName("Santiago");
        author.setNationality("coreano");
        author.setDate_birth(LocalDate.of(2000, 12, 30));

        Book book = new Book();
        book.setIsbn("96567-56234");
        book.setPrice(BigDecimal.valueOf(500));
        book.setGender(BookGender.MISTERIO);
        book.setTitle("misterio da semana");
        book.setPublicationDate(LocalDate.of(2022, 5, 10));
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setIsbn("45521-56234");
        book2.setPrice(BigDecimal.valueOf(440));
        book2.setGender(BookGender.MISTERIO);
        book2.setTitle("misterio do mes");
        book2.setPublicationDate(LocalDate.of(2023, 5, 10));
        book2.setAuthor(author);

        author.setBooks(new ArrayList<Book>());
        author.getBooks().add(book);
        author.getBooks().add(book2);


        repository.save(author);
        bookRepository.saveAll(author.getBooks());
    }

    @Test
    void bookList(){
        var id = UUID.fromString("97c8d920-afc6-41f0-8688-7f966defd289");
        var author = repository.findById(id).get();

        List<Book> list = bookRepository.findByAuthor(author);
        author.setBooks(list);
        author.getBooks().forEach(System.out::println);
    }
}
