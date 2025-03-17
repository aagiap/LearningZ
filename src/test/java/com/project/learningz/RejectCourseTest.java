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

public class RejectCourseTest {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup(); // Tự động tải ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/login");
    }

    @Test
    public void testRejectCourse() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // check login
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys("admin1");
        Thread.sleep(2000);
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("az123456");
        Thread.sleep(2000);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        loginButton.click();
        Thread.sleep(2000);
        WebElement summaryText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'The summary of Accounts')]")));
        Assert.assertTrue(summaryText.isDisplayed(), "Failed to sign in!");

        // check click vào link, vào được page list các Pending Course
        WebElement pendingCourseLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Pending courses")));
        pendingCourseLink.click();
        WebElement pendingCourseTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'PENDING')]")));
        Assert.assertTrue(pendingCourseTitle.isDisplayed(), "Failed to navigate pending course page!");

        // check reject without feedback
        WebElement approveButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-link.text-danger")));
        approveButton1.click();
        Thread.sleep(2000);
        Alert prompt1 = wait.until(ExpectedConditions.alertIsPresent());
        String promptText1 = prompt1.getText();
        Assert.assertTrue(promptText1.contains("Please enter feedback before rejecting:"), "Failed to reject this course!");
        prompt1.dismiss();
        Thread.sleep(2000);
        Alert prompt2 = wait.until(ExpectedConditions.alertIsPresent());
        String promptText2 = prompt2.getText();
        prompt2.accept();
        Thread.sleep(2000);
        Assert.assertTrue(promptText2.contains("You must provide feedback to reject the course."), "Failed to reject this course!");
        // check reject with feedback
        WebElement approveButton2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-link.text-danger")));
        approveButton2.click();
        Thread.sleep(2000);
        Alert prompt3 = wait.until(ExpectedConditions.alertIsPresent());
        String promptText3 = prompt3.getText();
        Assert.assertTrue(promptText3.contains("Please enter feedback before rejecting:"), "Failed to reject this course!");
        prompt3.sendKeys("Không đảm bảo nội dung");
        Thread.sleep(2000);
        prompt3.accept();
        Thread.sleep(2000);
        Alert prompt4 = wait.until(ExpectedConditions.alertIsPresent());
        String promptText = prompt4.getText();
        Assert.assertTrue(promptText.contains("Are you sure you want to reject this course?"), "Failed to reject this course!");
        prompt4.accept();
        Thread.sleep(2000);
        WebElement afterApprovement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Course has been rejected successfully!')]")));
        Assert.assertTrue(afterApprovement.isDisplayed(), "Failed to reject course!");
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Đóng trình duyệt sau khi test xong
        }
    }
}
