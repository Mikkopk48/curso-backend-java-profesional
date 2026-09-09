package dev.fintechlab.ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

@Tag("e2e") @Disabled("Requiere API, datos ficticios preparados y Chrome; ejecutar conscientemente en CI E2E")
class TransferPageE2ETest {
    @Test void showsAResultWithoutFixedSleeps(){WebDriver driver=new ChromeDriver(new ChromeOptions().addArguments("--headless=new"));try{driver.get("http://localhost:8080/");new WebDriverWait(driver,Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='transfer-submit']")));}finally{driver.quit();}}
}
