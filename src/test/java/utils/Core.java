package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class Core {

    protected static WebDriver driver;

    public static WebDriver setDriver(String browser) {

        switch (browser) {
            case "firefox":
                driver = WebDriverManager.firefoxdriver().create();
                break;
            case "chrome":
                driver = WebDriverManager.chromedriver().create();
                break;
            default:
                driver = WebDriverManager.chromedriver().create();
                break;
        }
        return driver;
    }

}
