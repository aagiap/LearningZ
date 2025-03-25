package com.project.learningz;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup(); // Tự động tải ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/login"); // Thay bằng URL trang login của bạn
    }

    @Test
    public void testLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys("admin1");
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("az123456");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        loginButton.click();
        // Chờ và kiểm tra xem có xuất hiện phần tử chứa "The summary of Accounts" không
        WebElement summaryText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'The summary of Accounts')]")));
        // Kiểm tra kết quả
        Assert.assertTrue(summaryText.isDisplayed(), "Failed to sign in!");
    }

    private void slowType(WebElement element, String text, int delayMillis) throws InterruptedException {
        for (char c : text.toCharArray()) {
            element.sendKeys(String.valueOf(c));
            Thread.sleep(delayMillis); // Chờ 0.2s giữa các ký tự
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Đóng trình duyệt sau khi test xong
        }
    }
}

