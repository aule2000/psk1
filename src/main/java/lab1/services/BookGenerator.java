package lab1.services;

import lab1.jpa.entities.Book;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.Random;

@ApplicationScoped
public class BookGenerator implements IBookGenerator, Serializable {

    public long generateId() {
        try{
            Random rand = new Random();
            long id = rand.nextLong(999999999);

            Thread.sleep(3000);
            return id;
        }catch (InterruptedException e){
            return 0;
        }


    }
}
