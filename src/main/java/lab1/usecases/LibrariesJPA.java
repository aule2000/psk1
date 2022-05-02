package lab1.usecases;

import lab1.jpa.entities.Library;
import lab1.jpa.persistence.LibrariesDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class LibrariesJPA {
    @Inject
    private LibrariesDAO librariesDAO;

    @Getter
    @Setter
    private Library libraryToCreate = new Library();

    @Getter
    private List<Library> allLibraries;

    @PostConstruct
    public void init() {
        loadAllLibraries();
    }

    public Library findById(int id) {
        return librariesDAO.findById(id);
    }

    @Transactional
    public void createLibrary() {
        librariesDAO.persist(libraryToCreate);
    }

    private void loadAllLibraries() {
        allLibraries = librariesDAO.loadAll();
    }
}