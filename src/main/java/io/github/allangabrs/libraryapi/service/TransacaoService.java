package io.github.allangabrs.libraryapi.service;

import io.github.allangabrs.libraryapi.model.Author;
import io.github.allangabrs.libraryapi.model.Book;
import io.github.allangabrs.libraryapi.model.BookGender;
import io.github.allangabrs.libraryapi.repository.AuthorRepository;
import io.github.allangabrs.libraryapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransacaoService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public void executar(){
        Author author = new Author();
        author.setName("Teste José");
        author.setNationality("marroquino");
        author.setDate_birth(LocalDate.now());

        authorRepository.save(author);

        Book book = new Book();
        book.setIsbn("90887-54854");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(BookGender.FICCAO);
        book.setTitle("livro do teste jose");
        book.setPublicationDate(LocalDate.of(2000, 10, 20));

        book.setAuthor(author);

        bookRepository.save(book);

        if(author.getName().equals("José")){
            throw new RuntimeException("Roolback");
        }
    }


}
