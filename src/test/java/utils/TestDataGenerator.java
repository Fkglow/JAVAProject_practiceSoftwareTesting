package utils;

import com.github.javafaker.Faker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestDataGenerator {

    private Faker faker;
    private Random random;

    public TestDataGenerator() {
        faker = new Faker();
        random = new Random();
    }

    public String getRandomProductName() {
        List<String> productNamesList = loadProductsNamesFromFile("./data/products.txt");
        int index = random.nextInt(productNamesList.size()-1);
        return productNamesList.get(index);
    }

    public String generateRandomString() {
        return faker.letterify("????");
    }

    public String generatePasswordShorterThan8Chars(){
        String pass = (faker.letterify("??", false))
                + (faker.letterify("??", true))
                + (faker.numerify("##"))
                + generateRandomSpecialChar();
        return pass;
    }

    public String generatePasswordWithoutUpperCase(){
        String pass = (faker.letterify("??????", false))
                + (faker.numerify("##"))
                + generateRandomSpecialChar();
        return pass;
    }

    public String generatePasswordWithoutNumber(){
        String pass = (faker.letterify("???????", false))
                + (faker.letterify("??", true))
                + generateRandomSpecialChar();
        return pass;
    }

    public String generatePasswordWithoutSpecialSymbol() {
        String pass = (faker.letterify("???????", false))
                + (faker.letterify("??", true))
                + (faker.numerify("##"));
        return pass;
    }

    public String generateCorrectPassword() {
        String pass = (faker.letterify("???????", false))
                + (faker.letterify("??", true))
                + (faker.numerify("##"))
                + generateRandomSpecialChar();
        return pass;
    }

    private Character generateRandomSpecialChar() {
        String chars = "!@#$%^&*()-_=+[]{};:,.<>?";
        int index = random.nextInt(chars.length()-1);
        return chars.charAt(index);
    }

    private List<String> loadProductsNamesFromFile(String filePath) {
        Path path = Paths.get(filePath);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Product names were not loaded from file");
            return Collections.emptyList();
        }
    }

}
