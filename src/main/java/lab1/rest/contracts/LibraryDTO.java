package lab1.rest.contracts;

import lab1.jpa.entities.Author;
import lab1.jpa.entities.Book;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class LibraryDTO {
    private int id;
    private String name;

    private List<BookDTO> books;

    private AuthorDTO authorsWithBooks;
}
