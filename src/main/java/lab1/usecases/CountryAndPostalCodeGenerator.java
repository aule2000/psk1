package lab1.usecases;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Named
public class CountryAndPostalCodeGenerator implements Serializable {


    private final Random rand = new Random();
    private CompletableFuture<String> generationTask = null;

    public void generateNewAddressName() {
        generationTask = CompletableFuture.supplyAsync(() -> generateCountryAndPostalCode());
    }

    private String generateCountryAndPostalCode() {
        try {
            Thread.sleep(5000);
            return "Lithuania, LT- " + rand.nextInt(99999);
        } catch(InterruptedException ignored) {
            return null;
        }

    }
    public boolean isNameGenerationRunning() {
        return generationTask != null && !generationTask.isDone();
    }

    public String getCountryAndPostalCodeNameGenerationStatus() throws ExecutionException, InterruptedException {
        if (generationTask == null) {
            return null;
        } else if (isNameGenerationRunning()) {
            return "Address is being generated...";
        }
        return "Address name suggestion: " + generationTask.get();
    }

    public String getResult() throws ExecutionException, InterruptedException {
        return generationTask.get();
    }
}
