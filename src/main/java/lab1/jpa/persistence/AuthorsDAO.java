package lab1.jpa.persistence;

import lab1.jpa.entities.Author;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class AuthorsDAO {
    @Inject
    private EntityManager em;

    public List<Author> loadAll() {
        return em.createNamedQuery("Author.findAll", Author.class).getResultList();
    }

    public void persist(Author author) {
        em.persist(author);
    }

    public Author findById(int id) {
        return em.find(Author.class, id);
    }
}