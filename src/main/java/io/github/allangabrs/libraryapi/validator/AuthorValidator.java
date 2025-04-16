package io.github.allangabrs.libraryapi.validator;

import io.github.allangabrs.libraryapi.excecptions.DuplicateRecordException;
import io.github.allangabrs.libraryapi.model.Author;
import io.github.allangabrs.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository repository;

    public AuthorValidator(AuthorRepository repository) {
        this.repository = repository;
    }

    public void validate(Author author){
        if(existsAuthor(author)){
            throw new DuplicateRecordException("Autor já cadastrado");
        }
    }

    private boolean existsAuthor(Author author){
        Optional<Author> optionalAuthor = repository.findByNameAndDateBirthAndNationality(
                author.getName(), author.getDateBirth(), author.getNationality()
        );

        if(author.getId() == null){
            return optionalAuthor.isPresent();
        }

        return !author.getId().equals(optionalAuthor.get().getId()) && optionalAuthor.isPresent();
    }
}
