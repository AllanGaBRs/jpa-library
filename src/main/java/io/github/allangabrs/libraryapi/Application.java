package io.github.allangabrs.libraryapi;

import io.github.allangabrs.libraryapi.model.Author;
import io.github.allangabrs.libraryapi.repository.AuthorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		var context = SpringApplication.run(Application.class, args);
		salvarRegistro(context.getBean(AuthorRepository.class));
	}

	public static void salvarRegistro(AuthorRepository repository){
		Author autor = new Author();
		autor.setName("aa");
		autor.setNationality("brasileiro");
		autor.setDate_birth(LocalDate.now());

		autor = repository.save(autor);
		System.out.println("Autor " + autor);
	}


}
