package lab1.rest.contracts;

import lab1.jpa.entities.Book;
import lab1.jpa.entities.Library;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AuthorDTO {

    private Integer id;

    private String name;
    private String lastName;
    private String fullAuthorName;

    private List<BookDTO> books;

    private LibraryDTO library;

}
