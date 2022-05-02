package lab1.usecases;

import lab1.jpa.entities.*;
import lab1.jpa.persistence.BooksDAO;
import lab1.jpa.persistence.LibrariesDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

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

    @PostConstruct
    public void init() {
        loadAllBooks();
        bookToCreate.setLibrary(new Library());
        bookToCreate.setAuthor(new Author());
    }

    public Book findById(int id) {
        return books.findById(id);
    }

    @Transactional
    public void createBook() {
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
