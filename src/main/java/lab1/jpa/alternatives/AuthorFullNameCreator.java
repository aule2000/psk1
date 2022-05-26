package lab1.jpa.alternatives;

import lab1.jpa.entities.Author;
import lab1.services.NameCreator;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@Alternative
@ApplicationScoped
public class AuthorFullNameCreator implements NameCreator {
    @Override
    public String generateName(Author author) {
        if (author == null) {
            throw new NullPointerException("Supplied author is null");
        }
        return author.getName() + ' ' + author.getLastName();
    }
}
