package io.github.allangabrs.libraryapi.service;

import io.github.allangabrs.libraryapi.controller.dto.AuthorDTO;
import io.github.allangabrs.libraryapi.excecptions.OperationNotPermittedException;
import io.github.allangabrs.libraryapi.model.Author;
import io.github.allangabrs.libraryapi.repository.AuthorRepository;
import io.github.allangabrs.libraryapi.repository.BookRepository;
import io.github.allangabrs.libraryapi.validator.AuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository,
                         AuthorValidator authorValidator,
                         BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.authorValidator = authorValidator;
        this.bookRepository = bookRepository;
    }

    public Author save(Author author) {
        authorValidator.validate(author);
        return authorRepository.save(author);
    }

    public void update(Author author) {
        if(author.getId() == null){
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    public void delete(Author author) {
        if(hasBook(author)){
            throw new OperationNotPermittedException(
                    "Não é permitido deletar um autor que possui livros cadastrados");
        }
        authorRepository.delete(author);
    }

    public List<Author> search(String name, String nationality) {
        if (name != null && nationality != null) {
            return authorRepository.findByNameAndNationality(name, nationality);
        }
        if (name != null) {
            return authorRepository.findByName(name);
        }
        if (nationality != null) {
            return authorRepository.findByNationality(nationality);
        }
        return authorRepository.findAll();
    }

    public List<Author> searchByExample(String name, String nationality){
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dateBirth")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> authorExample = Example.of(author, matcher);

        return authorRepository.findAll(authorExample);
    }

    public boolean hasBook(Author author){
        return bookRepository.existsByAuthor(author);
    }
}
