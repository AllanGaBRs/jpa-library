package io.github.allangabrs.libraryapi.service;

import io.github.allangabrs.libraryapi.controller.dto.AuthorDTO;
import io.github.allangabrs.libraryapi.model.Author;
import io.github.allangabrs.libraryapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

   private final AuthorRepository authorRepository;

   public AuthorService(AuthorRepository authorRepository){
       this.authorRepository = authorRepository;
   }

    public Author save(Author author){
       return authorRepository.save(author);
    }
}
