package lab1.jpa.persistence;

import lab1.jpa.entities.Author;
import lab1.jpa.entities.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BooksDAO{
    @Inject
    private EntityManager em;

    @Transactional
    public List<Book> loadAll() {
        return em.createNamedQuery("Book.findAll", Book.class).getResultList();
    }
    public List<Book> findByAuthorId(int authorId) {
        return em.createNamedQuery("Book.findByAuthorId", Book.class).
                setParameter("author", authorId).
                getResultList();
    }

    public void persist(Book book) {
        em.persist(book);
        em.flush();
    }

    public Book update(Book book){
        Book b = em.merge(book);
        em.flush();
        return b;
    }

    public Book findById(long id) {
        return em.find(Book.class, id);
    }


}
