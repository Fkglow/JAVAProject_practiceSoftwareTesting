package com.practicesoftwaretesting.tests;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.practicesoftwaretesting.pages.HomePage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FilterProductsByCategoryTest extends Core {

    public HomePage homePage;
    public CSVReader csvReader;

    @BeforeClass
    public void setUp() {
        driver = setDriver("chrome");
        driver.manage().window().maximize();
        driver.get("https://practicesoftwaretesting.com/");
        homePage = new HomePage(driver);
        homePage.waitForAllProductsToLoad();
    }

    @Test
    public void filterProductsByCategoryTest() {
        try {
            csvReader = new CSVReader(new FileReader("./data/filterByCategoryResults.csv"));
            csvReader.readNext();
            String[] record;
            while ( (record = csvReader.readNext()) != null ) {
                List<String> initialProducts = homePage.getProductsNamesFromTheList();
                homePage.selectCategoryCheckboxFilterByLabel(record[0]);
                homePage.waitForFilteringToComplete();
                homePage.waitForTableNamesToReload(initialProducts);
                homePage.waitForAllProductsToLoad();
                List<String> productsLabelsList = homePage.getProductsNamesFromTheList();
                List<String> expectedProducts = Arrays.asList(record[1].split(","));
                try {
                    Assert.assertEquals(productsLabelsList, expectedProducts);
                    List<String> initialProducts2 = homePage.getProductsNamesFromTheList();
                    homePage.selectCategoryCheckboxFilterByLabel(record[0]);
                    homePage.waitForFilteringToComplete();
                    homePage.waitForTableNamesToReload(initialProducts2);
                    homePage.waitForAllProductsToLoad();
                } catch (AssertionError ex) {
                    Reporter.log(ex.getMessage(), true);
                    throw ex;
                }
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
