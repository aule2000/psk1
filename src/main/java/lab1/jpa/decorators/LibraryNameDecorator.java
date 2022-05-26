package lab1.jpa.decorators;

import lab1.services.NameGenerator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.io.Serializable;

@Decorator
public class LibraryNameDecorator implements NameGenerator, Serializable {

    @Delegate @Inject @Any NameGenerator nameGenerator;
    @Override
    public String generateName(String name) {
        String newName = nameGenerator.generateName(name);
        return newName.substring(0, 1).toUpperCase() + newName.substring(1);
    }


}
