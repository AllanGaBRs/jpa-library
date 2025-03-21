package io.github.allangabrs.libraryapi.repository;

import io.github.allangabrs.libraryapi.model.Author;
import io.github.allangabrs.libraryapi.model.Book;
import io.github.allangabrs.libraryapi.model.BookGender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByAuthor(Author author);

    List<Book> findByTitle(String title);

    List<Book> findByIsbn(String isbn);

    List<Book> findByTitleAndPrice(String title, BigDecimal price);

    List<Book> findByTitleOrIsbnOrderByTitle(String title, String isbn);

    List<Book> findByPublicationDateBetween(LocalDate begin, LocalDate end);

    @Query(" select b from Book as b order by b.title, b.price ")
    List<Book> listAllOrderByTitleAndPrice();

    @Query("select a from Book b join b.author a ")
    List<Author> listAuthorsOfBooks();

    @Query("select distinct b.title from Book b")
    List<String> listNamesDistinctBooks();

    @Query("""
        select b.gender
        from Book b
        join b.author a
        where a.nationality = 'marroquino'
        order by b.gender
    """)
    List<String> listGendersAuthorsMarroquinos();

    //named parameters -> parametros nomeados
    @Query("select b from Book b where b.gender = :gender order by :paramOrder")
    List<Book> findByGender(
            @Param("gender")BookGender bookGender,
            @Param("paramOrder") String propertyName);

    //Positional Parameters
    @Query("select b from Book b where b.gender = ?1 order by ?2")
    List<Book> findByGenderPositionalParameters(BookGender bookGender, String propertyName);
}
