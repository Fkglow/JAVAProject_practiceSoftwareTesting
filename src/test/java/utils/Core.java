package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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

    public double getDoublePriceFromPriceStringWithCurrency(String price) {
        String priceWithoutCurrency = price.substring(1);
        return Double.parseDouble(priceWithoutCurrency);
    }

    /**
     * Take screenShot for error and save in filePath
     * @param filePath path where image must be saved
     * @throws IOException
     */
    public static void getScreenShotError(String filePath) {
        File dest = new File(ConfigPath.IMAGE_ERROR_PATH + filePath);
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(500)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(), "JPG", dest);
        }catch (IOException ex) {
            Reporter.log(ex.getMessage(), true);
        }
    }

}
