package lab1.services;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LibraryNameGenerator implements NameGenerator {

    public String generateName(String name) {

        String generatedName = name + " library";
        return generatedName;
    }

}
