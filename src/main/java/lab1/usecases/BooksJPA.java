package lab1.usecases;

import lab1.jpa.entities.*;
import lab1.jpa.interceptors.LoggedInvocation;
import lab1.jpa.persistence.BooksDAO;
import lab1.jpa.persistence.LibrariesDAO;
import lab1.services.BookGenerator;
import lab1.services.NameGenerator;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Model
public class BooksJPA {
    @Inject
    private BooksDAO books;

    @Inject
    private LibrariesDAO librariesDAO;

    @Getter
    @Setter
    private Book bookToCreate = new Book();

    @Getter
    private List<Book> allBooks;

    @Getter
    private List<Library> allLibraries;

    @Inject
    private BookGenerator bookGenerator;

    private CompletableFuture<String> loginNameGenerationTask = null;

    @Getter @Setter
    private long currentBookId;

    @PostConstruct
    public void init() {
        loadAllBooks();
        bookToCreate.setLibrary(new Library());
        bookToCreate.setAuthor(new Author());
    }

    public Book findById(long id) {
        return books.findById(id);
    }
    public List<Book> findByAuthorId(int authorId) {
        return books.findByAuthorId(authorId);
    }

    public int getBooks(int authorId){
        return findByAuthorId(authorId).size();
    }

    @Transactional
    public void createBook() {
        bookToCreate.setIsbnCode(bookGenerator.generateId());
        int id = bookToCreate.getLibrary().getId();
        Library library = librariesDAO.findById(id);
        library.getAuthorsWithBooks().add(bookToCreate.getAuthor());
        books.persist(bookToCreate);
        librariesDAO.update(library);
    }

    private void loadAllBooks() {
        allBooks = books.loadAll();
        allLibraries = librariesDAO.loadAll();
    }

}
