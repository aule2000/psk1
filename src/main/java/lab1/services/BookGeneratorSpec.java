package lab1.services;

import lab1.jpa.entities.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import java.util.Random;

@Specializes
@ApplicationScoped
public class BookGeneratorSpec extends BookGenerator{

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
