package com.project.learningz;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
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

public class ApproveCourseTest {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup(); // Tự động tải ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/login");
    }

    @Test
    public void testApproveCourse() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // check login
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys("admin1");
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("az123456");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        loginButton.click();
        WebElement summaryText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'The summary of Accounts')]")));
        Assert.assertTrue(summaryText.isDisplayed(), "Failed to sign in!");

        // check click vào link, vào được page list các Pending Course
        WebElement pendingCourseLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Pending courses")));
        pendingCourseLink.click();
        WebElement pendingCourseTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'PENDING')]")));
        Assert.assertTrue(pendingCourseTitle.isDisplayed(), "Failed to navigate pending course page!");

        // check approve course (dismiss)
        WebElement approveButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-link.text-success")));
        approveButton1.click();
        Alert prompt1 = wait.until(ExpectedConditions.alertIsPresent());
        String promptText1 = prompt1.getText();
        Assert.assertTrue(promptText1.contains("Are you sure you want to approve this course?"), "Failed to approve this course!");
        prompt1.dismiss();
        WebElement afterApprovement1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'PENDING')]")));
        Assert.assertTrue(afterApprovement1.isDisplayed(), "Failed to approve course!");

        // check approve course (accept)
        WebElement approveButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-link.text-success")));
        approveButton2.click();
        Alert prompt2 = wait.until(ExpectedConditions.alertIsPresent());
        String promptText2 = prompt2.getText();
        Assert.assertTrue(promptText2.contains("Are you sure you want to approve this course?"), "Failed to approve this course!");
        prompt2.accept();              // chờ đến khi hiện ra phần tử/ hết thời gian tối đa đã cài đặt                    // //* => lấy tất cả các phần tử chứa ...
        WebElement afterApprovement2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Course has been approved successfully!')]")));
        Assert.assertTrue(afterApprovement2.isDisplayed(), "Failed to approve course!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // đóng trình duyệt sau khi test
        }
    }
}
