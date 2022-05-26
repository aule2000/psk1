package lab1.usecases;

import lab1.jpa.entities.Author;
import lab1.jpa.interceptors.LoggedInvocation;
import lab1.jpa.persistence.AuthorsDAO;
import lab1.services.NameCreator;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

import static java.lang.System.out;

@Model

@LoggedInvocation
public class AuthorsJPA {
    @Inject
    private AuthorsDAO authors;

    @Getter
    @Setter
    private Author authorToCreate = new Author();

    @Inject
    private NameCreator nameCreator;

    @Getter
    private List<Author> allAuthors;

    @Getter
    @Setter
    private Author authorToEdit = new Author();

    @PostConstruct
    public void init() {
        loadAllAuthors();
    }

    public Author findById(int id) {
        return authors.findById(id);
    }

    @Transactional
    public void createAuthor() {
        authorToCreate.setFullAuthorName(nameCreator.generateName(authorToCreate));
        authors.persist(authorToCreate);
    }

    private void loadAllAuthors() {
        allAuthors = authors.loadAll();
        if (allAuthors.size() >= 1){
            authorToEdit = allAuthors.get(0);
        }
    }


    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public String saveAuthorChanges() throws InterruptedException {
        authorToEdit.setFullAuthorName(nameCreator.generateName(authorToEdit));
        out.println("sleeping for 5 seconds");
        Thread.sleep(5000);
        try {
            authors.update(authorToEdit);
            return "authors?faces-redirect=true";
        } catch (OptimisticLockException e) {
            out.println("Caught optimistic lock exception:\n" + e);
            return "edit-author?faces-redirect=true" + "&error=optimistic-lock-exception";
        }
    }
}

