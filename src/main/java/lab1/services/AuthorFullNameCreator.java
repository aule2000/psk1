package lab1.services;

import lab1.jpa.entities.Author;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
public class AuthorFullNameCreator implements Serializable {
    /**
     * Assigns full author name based on author name and author lastName (JPA version).
     *
     * @param author city to which full names should be assigned
     */
    public void createFullAuthorName(Author author) {
        if (author == null) {
            throw new NullPointerException("Supplied author is null");
        }
        String authorName = author.getName();
        if (authorName == null) {
            throw new NullPointerException("Author's name is null");
        }
        String authorLastName = author.getLastName();
        if (authorLastName == null) {
            throw new NullPointerException("Author's lastName is null");
        }

        author.setFullAuthorName(generateName(authorName, authorLastName));
    }

    /**
     * Assigns full author name based on author name and author lastName (MyBatis version).
     *
     * @param author city to which full names should be assigned
     */
    public void createFullAuthorName(lab1.mybatis.model.Author author) {
        if (author == null) {
            throw new NullPointerException("Supplied author is null");
        }
        String authorName = author.getName();
        if (authorName == null) {
            throw new NullPointerException("Author's name is null");
        }
        String authorLastName = author.getLastname();
        if (authorLastName == null) {
            throw new NullPointerException("Author's lastName is null");
        }

        author.setFullAuthorName(generateName(authorName, authorLastName));
    }

    private String generateName(String authorName, String authorLastName) {
        return authorName + ' ' + authorLastName;
    }
}
