package lab1.usecases;

import lab1.mybatis.dao.AuthorMapper;
import lab1.mybatis.dao.BookMapper;
import lab1.mybatis.dao.LibraryMapper;
import lab1.mybatis.model.Book;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class BooksMyBatis {

    @Inject
    private BookMapper bookMapper;

    @Inject
    private AuthorMapper authorMapper;

    @Inject
    private LibraryMapper libraryMapper;

    @Getter
    @Setter
    private Book bookToCreate = new Book();

    @Getter
    private List<Book> allBooks;

    @PostConstruct
    public void init() {
        loadAllBooks();
    }

    public Book findById(int id) {
        return bookMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public void createBook() {
        bookMapper.insert(bookToCreate);
    }

    private void loadAllBooks() {
        allBooks = bookMapper.selectAll();
    }

}
