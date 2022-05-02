package lab1.usecases;

import lab1.mybatis.dao.LibraryMapper;
import lab1.mybatis.model.Library;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class LibrariesMyBatis {
    @Inject
    private LibraryMapper libraryMapper;


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
        return libraryMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public void createLibrary() {
        libraryMapper.insert(libraryToCreate);
    }

    private void loadAllLibraries() {
        allLibraries = libraryMapper.selectAll();
    }
}
