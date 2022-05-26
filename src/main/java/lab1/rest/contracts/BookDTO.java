package lab1.rest.contracts;

import lab1.jpa.entities.Author;
import lab1.jpa.entities.Library;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookDTO {
    private long id;

    private String name;

    private AuthorDTO author;

    private LibraryDTO library;

    private long isbnCode;

}
