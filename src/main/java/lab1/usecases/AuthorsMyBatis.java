package lab1.usecases;


import lab1.mybatis.dao.AuthorMapper;
import lab1.mybatis.model.Author;
import lab1.services.AuthorFullNameCreator;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class AuthorsMyBatis {
    @Inject
    private AuthorMapper authorMapper;

    @Getter
    @Setter
    private Author authorToCreate = new Author();

    @Inject
    private AuthorFullNameCreator authorFullNameCreator;

    @Getter
    private List<Author> allAuthors;

    @PostConstruct
    public void init() {
        loadAllAuthors();
    }

    public Author findById(int id) {
        return authorMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public void createAuthor() {
        authorFullNameCreator.createFullAuthorName(authorToCreate);
        authorMapper.insert(authorToCreate);
    }

    private void loadAllAuthors() {
        allAuthors = authorMapper.selectAll();
    }

}
