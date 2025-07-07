package Tests;

import Pages.GuestInfoPage;
import Pages.HotelSearchPage;
import Pages.PaymentPage;
import Utils.LogHelper;
import Utils.RMethods;
import io.qameta.allure.Allure;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static Utils.LogHelper.logger;

public class HotelReservationTest extends BaseTest {

    @Test
    @Story("HotelBookingHappyPathTest")
    public void hotelBookingHappyPathTest() throws InterruptedException {

        // Sayfa ve yardımcı sınıflar başlatılıyor
        RMethods utils = new RMethods();
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        GuestInfoPage guestInfoPage = new GuestInfoPage(driver);
        PaymentPage paymentPage = new PaymentPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        // Ziyaretçi "Otel" butonuna tıklar
        Allure.step("Ziyaretçi Otel butonuna tıklar");
        utils.clickElementByDataId("endesign-[unnamed]-tab-button-1");
        logger.info("Ziyaretçi Otel butonuna tıklar");

        // Ziyaretçi şehir, tarih ve konuk sayısı bilgilerini girer, ardından "Otel Bul" butonuna tıklar
        Allure.step("Ziyaretçi şehir, tarih ve konuk sayısı bilgilerini girer, ardından Otel Bul butonuna tıklar");
        hotelSearchPage.hotelReservation();
        logger.info(" Ziyaretçi şehir, tarih ve konuk sayısı bilgilerini girer, ardından Otel Bul butonuna tıklar");

        // Ziyaretçi fiyatları azalan şekilde sıralar
        Allure.step("Ziyaretçi fiyatları azalan şekilde sıralar");
        utils.clickElementByDataId("sort-fiyat-azalan-button");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@data-testid='hotel-select-button'])[1]")));
        logger.info("Ziyaretçi fiyatları azalan şekilde sıralar");

        // Ziyaretçi listeden rastgele bir otel seçer
        Allure.step("Ziyaretçi listeden rastgele bir otel seçer");
        hotelSearchPage.randomSelectHotel();
        logger.info("Ziyaretçi listeden rastgele bir otel seçer");


        // Ziyaretçi en uygun oda için "Odayı Ayır" butonunu görür ve tıklar
        Allure.step("Ziyaretçi en uygun oda için Odayı Ayır butonunu görür ve tıklar");
        By roomCheckButtonLocator = By.xpath("(//*[@data-testid='offer-select-room-button'])[1]");
        wait.until(ExpectedConditions.elementToBeClickable(roomCheckButtonLocator));
        WebElement roomCheckButton = driver.findElement(roomCheckButtonLocator);
        logger.info("Ziyaretçi en uygun oda için Odayı Ayır butonunu görür ve tıklar");

        utils.scrollTo(hotelSearchPage.roomCheckButton);
        Assert.assertTrue(hotelSearchPage.roomCheckButton.isDisplayed(), " Oda seçme butonu görünmüyor!");
        hotelSearchPage.roomCheckButton.click();

        // Ziyaretçi konuk bilgileri sayfasına yönlendirilir ve formu doldurur
        Allure.step("Ziyaretçi konuk bilgileri sayfasına yönlendirilir ve formu doldurur");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@data-testid='contact-email']")).isDisplayed());
        guestInfoPage.inputGuestDetails();
        logger.info("Ziyaretçi konuk bilgileri sayfasına yönlendirilir ve formu doldurur");

        // Ziyaretçi "Ödemeye İlerle" butonuna tıklar
        Allure.step("Ziyaretçi Ödemeye İlerle butonuna tıklar");
        utils.clickElementByDataId("reservation-form-submit-button");
        wait.until(ExpectedConditions.visibilityOf(paymentPage.cartNoButton));
        logger.info("Ziyaretçi Ödemeye İlerle butonuna tıklar");

        // 🧾 Ziyaretçi konuk bilgilerini doğrular
        Allure.step("Ziyaretçi konuk bilgilerini doğrular");
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(
                guestInfoPage.getMail(),
                paymentPage.mailButton.getAttribute("value"),
                " Mail adresi uyuşmuyor!"
        );


        utils.clickElementByDataId("toggle-content");   // 1.kisi bilgilerine tiklar

        softAssert.assertEquals(
                guestInfoPage.getName(),
                paymentPage.nameButton.getAttribute("value"),
                " İsim uyuşmuyor!"
        );

        softAssert.assertEquals(
                guestInfoPage.getLastname(),
                paymentPage.lastnameButton.getAttribute("value"),
                " Soyisim uyuşmuyor!"
        );
        logger.info("Ziyaretçi konuk bilgilerini doğrular");


        //  Ziyaretçi seçilen otel adını ödeme sayfasında doğrular
        Allure.step("Ziyaretçi seçilen otel adını ödeme sayfasında doğrular");
        String actualHotelName = paymentPage.hotelName.getText();
        System.out.println("Seçilen otel adı      : " + hotelSearchPage.selectedHotelName);
        System.out.println("Ödeme sayfası otel adı: " + actualHotelName);

        softAssert.assertEquals(
                actualHotelName,
                hotelSearchPage.selectedHotelName,
                " Otel adı uyuşmuyor!"
        );
        logger.info("iyaretçi seçilen otel adını ödeme sayfasında doğrular");

        //  Giriş ve çıkış tarihlerini ödeme sayfasında doğrular
        Allure.step("Giriş ve çıkış tarihlerini ödeme sayfasında doğrular");
        String actualCheckInDate = paymentPage.checkinDate.getText();
        String actualCheckOutDate = paymentPage.checkOutDate.getText();

        System.out.println("Seçilen Giriş Tarihi      : " + hotelSearchPage.checkInDatePretty);
        System.out.println("Ödeme Sayfası Giriş Tarihi: " + actualCheckInDate);
        System.out.println("Seçilen Çıkış Tarihi      : " + hotelSearchPage.checkOutDatePretty);
        System.out.println("Ödeme Sayfası Çıkış Tarihi: " + actualCheckOutDate);

        Assert.assertEquals(
                actualCheckInDate,
                hotelSearchPage.checkInDatePretty,
                " Giriş tarihi uyuşmuyor!"
        );
        Assert.assertEquals(
                actualCheckOutDate,
                hotelSearchPage.checkOutDatePretty,
                " Çıkış tarihi uyuşmuyor!"
        );
        logger.info("Giriş ve çıkış tarihlerini ödeme sayfasında doğrular");

        softAssert.assertAll();

        //  Testin başarıyla tamamlandığını bildir
        System.out.println(" Otel rezervasyonu testi başarıyla tamamlandı..");


    }
}


