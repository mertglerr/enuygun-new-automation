package Pages;

import Utils.RMethods;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GuestInfoPage {
    RMethods utils = new RMethods();
    WebDriver driver;

    public GuestInfoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Bu satır çok önemli
    }

    private String mail;
    private String phone;
    private String name;
    private String lastname;

    @FindBy(xpath = "(//span[@name='rooms.0.travellers.0.gender'])[1]")
    public WebElement maleButton;

    public void inputGuestDetails() {
        Faker faker = new Faker();

        // Faker verilerini sınıf değişkenlerine ata
        this.mail = faker.internet().emailAddress();
        this.phone = "550000000";
        this.name = faker.name().firstName();
        this.lastname = faker.name().lastName();

        // Verileri inputlara gönder
        utils.clickAndSendKeysByDataId("contact-email", this.mail);
        utils.clickAndSendKeysByDataId("contactPhone", this.phone);
        utils.clickAndSendKeysByDataId("room-first-name", this.name);
        utils.clickAndSendKeysByDataId("room-last-name", this.lastname);

        maleButton.click();
    }

    // getter metotları
    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }
}
