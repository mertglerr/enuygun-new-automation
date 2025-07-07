package Tests;

import Pages.HomePage;
import Pages.HomePageCar;
import Utils.DriverFactory;
import Utils.RMethods;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.sql.DriverManager;

public class carRentalTest {

    HomePageCar homePageCar = new HomePageCar();


    @Test(priority = 1, description = "")
    public void carSelectionTest() throws InterruptedException {
        DriverFactory.getDriver().get("https://www.enuygun.com/");
        Thread.sleep(5000);
        homePageCar.cookieAccept.click();
        Thread.sleep(5000);
        homePageCar.homepageCarRentalBtn.click();

    }

}
