package Pages;

import Tests.BaseTest;
import Utils.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageCar extends BaseTest {

    public HomePageCar(){
        PageFactory.initElements(DriverFactory.getDriver(),this);
    }

    @FindBy(id = "headlessui-tabs-tab-:R29ks556:")
    public WebElement homepageCarRentalBtn;

    @FindBy(id = "onetrust-accept-btn-handler")
    public WebElement cookieAccept;
}