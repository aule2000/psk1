package lab1.jpa.persistence;

import lab1.jpa.entities.Library;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class LibrariesDAO {
    @Inject
    private EntityManager em;

    public List<Library> loadAll() {
        return em.createNamedQuery("Library.findAll", Library.class).getResultList();
    }

    public void persist(Library library) {
        em.persist(library);
    }

    public void update(Library library) {
        em.merge(library);
    }

    public Library findById(int id) {
        return em.find(Library.class, id);
    }
}
