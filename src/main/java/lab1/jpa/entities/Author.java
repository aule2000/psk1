package lab1.jpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Author.findAll", query = "select c from Author c")
})
@Table
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String lastName;        //added

    @Column(name = "full_author_name", nullable = false)
    private String fullAuthorName;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    @ManyToMany(mappedBy = "authorsWithBooks")
    private Set<Library> libraries;

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (!(other instanceof Author o)) {
            return false;
        }
        return o.id == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
