package io.github.allangabrs.libraryapi.controller;

import io.github.allangabrs.libraryapi.controller.dto.AuthorDTO;
import io.github.allangabrs.libraryapi.controller.dto.ResponseError;
import io.github.allangabrs.libraryapi.excecptions.DuplicateRecordException;
import io.github.allangabrs.libraryapi.excecptions.OperationNotPermittedException;
import io.github.allangabrs.libraryapi.model.Author;
import io.github.allangabrs.libraryapi.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody AuthorDTO authorDTO) {
        try {

            Author author = authorDTO.entity();
            service.save(author);

            //http://localhost:8080/authors/20eec8e5-5d3c-4ec6-9ac0-c4b4b5febada
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(author.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (DuplicateRecordException e) {
            var errorDTO = ResponseError.conflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable("id") String id) {
        Optional<Author> authorOptional = service.findById(UUID.fromString(id));
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            AuthorDTO dto = new AuthorDTO(
                    author.getId(),
                    author.getName(),
                    author.getDateBirth(),
                    author.getNationality()
            );
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        try {
            Optional<Author> authorOptional = service.findById(UUID.fromString(id));
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.delete(authorOptional.get());
            return ResponseEntity.noContent().build();
        } catch (OperationNotPermittedException e) {
            var error = ResponseError.patternResponse(e.getMessage());
            return ResponseEntity.status(error.status()).body(error);
        }
    }
        @GetMapping
        public ResponseEntity<List<AuthorDTO>> search (
                @RequestParam(value = "name", required = false) String name,
                @RequestParam(value = "nationality", required = false) String nationality){
            List<Author> result = service.search(name, nationality);
            List<AuthorDTO> list = result
                    .stream()
                    .map(author -> new AuthorDTO(
                            author.getId(),
                            author.getName(),
                            author.getDateBirth(),
                            author.getNationality())
                    ).collect(Collectors.toList());
            return ResponseEntity.ok(list);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Object> update (
                @PathVariable String id, @RequestBody AuthorDTO dto){
            try {

                var idAuthor = UUID.fromString(id);
                Optional<Author> authorOptional = service.findById(idAuthor);
                if (authorOptional.isEmpty()) {
                    return ResponseEntity.notFound().build();
                }

                var author = authorOptional.get();
                author.setName(dto.name());
                author.setNationality(dto.nationality());
                author.setDateBirth(dto.dateBirth());

                service.update(author);
                return ResponseEntity.noContent().build();
            } catch (DuplicateRecordException e) {
                var errorDTO = ResponseError.conflict(e.getMessage());
                return ResponseEntity.status(errorDTO.status()).body(errorDTO);
            }
        }
    }
