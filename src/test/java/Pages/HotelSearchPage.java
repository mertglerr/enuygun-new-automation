package Pages;

import Utils.RMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class HotelSearchPage {

    RMethods utils = new RMethods();
    WebDriver driver;

    public HotelSearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String checkInDatePretty;
    public String checkOutDatePretty;
    public String selectedHotelName;


    @FindBy(xpath = "(//*[@data-testid='offer-select-room-button'])[1]")
    public WebElement roomCheckButton;

    public void selectCheckInAndCheckOutDates() {
        LocalDate today = LocalDate.now();
        LocalDate checkInDate = today.plusDays(3);
        LocalDate checkOutDate = today.plusDays(6);

        DateTimeFormatter backendFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String checkInDateStr = checkInDate.format(backendFormat);
        String checkOutDateStr = checkOutDate.format(backendFormat);

        // Sadece kullanıcıya görünen formatta class-level değişkenlere yaz
        DateTimeFormatter prettyFormat = DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("tr", "TR"));
        checkInDatePretty = checkInDate.format(prettyFormat);    // örnek: 24 Temmuz 2025
        checkOutDatePretty = checkOutDate.format(prettyFormat);  // örnek: 27 Temmuz 2025

        utils.clickElementByDataId("hotel-datepicker-input");
        utils.waitFor(2000);

        WebElement checkInButton = driver.findElement(By.xpath("//button[@title='" + checkInDateStr + "']"));
        Assert.assertTrue(checkInButton.isDisplayed(), "Giriş tarihi butonu görünmüyor: " + checkInDateStr);
        checkInButton.click();

        WebElement checkOutButton = driver.findElement(By.xpath("//button[@title='" + checkOutDateStr + "']"));
        Assert.assertTrue(checkOutButton.isDisplayed(), "Çıkış tarihi butonu görünmüyor: " + checkOutDateStr);
        checkOutButton.click();
    }

    public void hotelReservation() throws InterruptedException {

        // arama butonuna tiklar ve izmir yazar
        utils.clickAndSendKeysByDataId("endesign-hotel-autosuggestion-input", "izmir");
        utils.waitFor(1000);

        // izmir seçeneğine tiklar
        utils.clickElementByDataId("endesign-hotel-autosuggestion-option-item-0");
        utils.waitFor(1000);

        // tarih seçimi yapar
        selectCheckInAndCheckOutDates();
        utils.waitFor(1000);

        // Konuk sayisi secimi yapar
        utils.clickElementByDataId("hotel-popover-button");
        utils.waitFor(500);
        utils.clickElementByDataId("hotel-adult-counter-minus-button");
        utils.waitFor(500);
        utils.clickElementByDataId("hotel-guest-submit-button");
        utils.waitFor(500);

        // otel bul butonuna tiklar
        RMethods.clickByExactText("Otel bul");

        // 'sec' butonu görünene kadar bekle (en fazla 10 saniye)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='sc-dAlyuH jUGfXx'])[1]")));

        // Artık 'sec' butonu görünüyor, buradan sonrası devam edebilir
    }

    public void randomSelectHotel() {
        SoftAssert softAssert = new SoftAssert();

        // Tüm otel başlıklarını al
        List<WebElement> hotelButtons = driver.findElements(By.xpath("//*[@data-testid='result-title']"));
        Assert.assertFalse(hotelButtons.isEmpty(), " Otel listesi boş!");

        // Rastgele seçim
        Random rand = new Random();
        int randomIndex = rand.nextInt(hotelButtons.size());

        // Seçilen otelin adını al ve class-level değişkene ata
        selectedHotelName = hotelButtons.get(randomIndex).getText();

        // Oteli tıkla
        hotelButtons.get(randomIndex).click();

        // Yeni sekmeye geç
        utils.switchToNewTab();
        utils.waitFor(2000);

        // Yeni sekmede görünen otel adını al
        String el2 = driver.findElement(By.xpath("//*[@data-testid='hotel-title']")).getText();

        // Karşılaştır
        softAssert.assertEquals(el2, selectedHotelName, " Otel başlıkları eşleşmiyor!");

        // Sonuçları bildir
        softAssert.assertAll();
    }


}
