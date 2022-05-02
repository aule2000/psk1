package lab1.usecases;

import lab1.jpa.entities.Author;
import lab1.jpa.persistence.AuthorsDAO;
import lab1.services.AuthorFullNameCreator;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class AuthorsJPA {
    @Inject
    private AuthorsDAO authors;

    @Getter
    @Setter
    private Author authorToCreate = new Author();

    @Getter
    private List<Author> allAuthors;

    @Inject
    private AuthorFullNameCreator authorFullNameCreator;

    @PostConstruct
    public void init() {
        loadAllAuthors();
    }

    public Author findById(int id) {
        return authors.findById(id);
    }

    @Transactional
    public void createAuthor() {
        authorFullNameCreator.createFullAuthorName(authorToCreate);
        authors.persist(authorToCreate);
    }

    private void loadAllAuthors() {
        allAuthors = authors.loadAll();
    }
}

