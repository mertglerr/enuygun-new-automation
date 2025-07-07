package Utils;

import Tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.sql.DriverManager;
import java.time.Duration;
import java.util.Set;

public class RMethods extends BaseTest {






































































































































































































    public void clickElementByDataId(String elementId) {
        // XPath'i id parametresinden oluştur
        String xpath = "//*[@data-testid='" + elementId + "']";

        // Elementi bul
        WebElement element = driver.findElement(By.xpath(xpath));

        // Görünürlük kontrolü (opsiyonel)
        Assert.assertTrue(element.isDisplayed(), "Element görünmüyor: " + elementId);

        // Tıkla
        element.click();
    }

    public void clickAndSendKeysByDataId(String elementId, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String xpath = "//*[@data-testid='" + elementId + "']";

        // Elementin varlığını ve görünürlüğünü bekle
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

        // Eğer element input veya textarea ise tıklamaya gerek yok, değilse tıkla
        String tagName = element.getTagName();
        if (!tagName.equalsIgnoreCase("input") && !tagName.equalsIgnoreCase("textarea")) {
            element.click();
        }

        // Metin gönder
        element.sendKeys(text);
    }

    public static void clickByExactText(String text) {
        WebElement element = driver.findElement(By.xpath("//*[text()='" + text + "']"));
        element.click();
    }

    public void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // Thread beklenmedik şekilde kesilirse, interrupt durumunu geri bildiriyoruz
            Thread.currentThread().interrupt();
            System.out.println("Wait interrupted: " + e.getMessage());
        }
    }

    public void switchToNewTab() {
        // Tüm pencere/sekme handle'larını al
        Set<String> windowHandles = driver.getWindowHandles();
        String currentHandle = driver.getWindowHandle();

        // Yeni açılan sekmenin handle'ını bulmak için
        for (String handle : windowHandles) {
            if (!handle.equals(currentHandle)) {
                // Yeni sekmeye geç
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'})", element);
    }












}
