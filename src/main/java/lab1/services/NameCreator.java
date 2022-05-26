package lab1.services;

import lab1.jpa.entities.Author;

import java.io.Serializable;

public interface NameCreator extends Serializable {
        String generateName(Author author);
}
