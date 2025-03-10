package io.github.allangabrs.libraryapi.repository;

import io.github.allangabrs.libraryapi.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

    @Test
    public void salvarTest(){
        Author autor = new Author();
        autor.setName("allan");
        autor.setNationality("ingles");
        autor.setDate_birth(LocalDate.now());

        autor = repository.save(autor);
        System.out.println("Autor " + autor);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("eeb3d705-c280-4597-aa2f-e8fac7dc24b6");

        Optional<Author> possibleAuthor = repository.findById(id);

        if(possibleAuthor.isPresent()){
            Author author = possibleAuthor.get();

            System.out.println("Dados do autor");
            System.out.println(author);

            author.setName("Allan");
            author.setDate_birth(LocalDate.of(2020, 10,10));
            repository.save(author);
        }
    }

}
