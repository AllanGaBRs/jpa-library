package io.github.allangabrs.libraryapi.repository;

import io.github.allangabrs.libraryapi.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository repository;

    @Test
    public void saveTest(){
        Author author = new Author();
        author.setName("allan");
        author.setNationality("ingles");
        author.setDate_birth(LocalDate.now());

        author = repository.save(author);
        System.out.println("Autor " + author);
    }

    @Test
    public void updateTest(){
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

    @Test
    public void listTest(){
        List<Author> list = repository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deleteByIdTest(){
        var id = UUID.fromString("0d2acd9e-ea0f-4f09-841e-2f10de2f6d2c");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("5554ee52-94d3-45a0-a5fb-96e75ff9390a");
        var author = repository.findById(id).get();
        repository.delete(author);
    }
}
