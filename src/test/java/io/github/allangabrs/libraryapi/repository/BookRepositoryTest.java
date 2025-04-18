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
import java.util.List;
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
        book.setGender(BookGender.CIENCIA);
        book.setTitle("CIENCIA da REGI");
        book.setPublicationDate(LocalDate.of(2000, 10, 20));

        Author author = authorRepository
                .findById(UUID.fromString("fafa73a7-98e6-427a-9838-5ebbd42f592d"))
                .orElse(null);

        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    void saveAuthorAndBookTest() {
        Book book = new Book();
        book.setIsbn("90887-54854");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(BookGender.FICCAO);
        book.setTitle("author and book without cascade");
        book.setPublicationDate(LocalDate.of(2000, 10, 20));

        Author author = new Author();
        author.setName("Felipe");
        author.setNationality("marroquino");
        author.setDateBirth(LocalDate.now());
        authorRepository.save(author);

        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    void saveCascadeTest() {
        Book book = new Book();
        book.setIsbn("90887-54854");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(BookGender.FICCAO);
        book.setTitle("book cascade");
        book.setPublicationDate(LocalDate.of(2000, 10, 20));

        Author author = new Author();
        author.setName("João");
        author.setNationality("ingles");
        author.setDateBirth(LocalDate.now());

        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    void updateAuthor(){
        var book = bookRepository.findById(
                UUID.fromString("8059fa49-bda3-4d0e-93e6-8e114c81b8f0"))
                .orElse(null);
        Author author = authorRepository.findById(
                UUID.fromString("9cda0c00-1f3b-438a-ac6c-ff356a7c4530"))
                .orElse(null);

        book.setAuthor(author);
        bookRepository.save(book);
    }

    @Test
    @Transactional
    void findBook(){
        UUID id = UUID.fromString("524a1f9f-5997-4465-abb5-5542b35b0307");
        Book book = bookRepository.findById(id).orElse(null);

        System.out.println("Book:");
        System.out.println(book.getTitle());
        System.out.println("author:");
        System.out.println(book.getAuthor().getName());

    }

    @Test
    void findByTitleTest(){
        List<Book> list = bookRepository.findByTitle("misterio da semana");
        list.forEach(System.out::println);
    }

    @Test
    void findByIsbnTest(){
        List<Book> list = bookRepository.findByIsbn("90887-54854");
        list.forEach(System.out::println);

    }

    @Test
    void findByTitleAndPriceTest(){
        List<Book> list = bookRepository.findByTitleAndPrice("misterio do mes", BigDecimal.valueOf(440));
        list.forEach(System.out::println);
    }

    @Test
    void listBooksWithQueryJPQL(){
        var result = bookRepository.listAllOrderByTitleAndPrice();
        result.forEach(System.out::println);
    }

    @Test
    void listAuthorsOfBooks(){
        var result = bookRepository.listAuthorsOfBooks();
        result.forEach(System.out::println);
    }

    @Test
    void listNamesDistinctBooks(){
        var result = bookRepository.listNamesDistinctBooks();
        result.forEach(System.out::println);
    }

    @Test
    void listGendersAuthorsMarroquinos(){
        var result = bookRepository.listGendersAuthorsMarroquinos();
        result.forEach(System.out::println);
    }

    @Test
    void listByGenderQueryParamTest(){
        var result = bookRepository.findByGender(BookGender.FICCAO, "publicationDate");
        result.forEach(System.out::println);
    }

    @Test
    void listByGenderQueryPositionalParamTest(){
        var result = bookRepository.findByGenderPositionalParameters(BookGender.FICCAO, "publicationDate");
        result.forEach(System.out::println);
    }

    @Test
    void deleteByGenderTest(){
        bookRepository.deleteByGender(BookGender.CIENCIA);
    }

    @Test
    void updatePublicationDateTest(){
        bookRepository.updatePublicationDate(LocalDate.now());
    }
}
