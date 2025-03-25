package com.project.learningz;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class AddUserTest {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup(); // Tự động tải ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/login");
    }

    @Test
    public void testApproveCourse() throws InterruptedException {
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

        // check Cancel
        WebElement allUsersLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("All Users")));
        allUsersLink.click();
        WebElement allUserTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Add User')]")));
        Assert.assertTrue(allUserTitle.isDisplayed(), "Failed to navigate all user page!");
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Add User')]")));
        addButton.click();
        WebElement allUserPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Add New User')]")));
        Assert.assertTrue(allUserPage.isDisplayed(), "Failed to navigate add user page!");
        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-secondary")));
        cancelButton.click();
        WebElement checkAfterCancel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'The summary of Accounts')]")));
        Assert.assertTrue(checkAfterCancel.isDisplayed(), "Failed to navigate cheese1 page!");

        // check click vào link, vào được page all user to show button add
        WebElement allUsersLink1 = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("All Users")));
        allUsersLink1.click();
        WebElement allUserTitle1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Add User')]")));
        Assert.assertTrue(allUserTitle1.isDisplayed(), "Failed to navigate all user page!");
        // check add process
        WebElement addButton1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(), 'Add User')]")));
        addButton1.click();
        WebElement allUserPage1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Add New User')]")));
        Assert.assertTrue(allUserPage1.isDisplayed(), "Failed to navigate add user page!");
        Thread.sleep(2000);

        // input sai pass
        driver.findElement(By.name("username")).sendKeys("cheese2");
        driver.findElement(By.name("password")).sendKeys("az123");
        driver.findElement(By.name("phone")).sendKeys("987653210");
        driver.findElement(By.name("email")).sendKeys("cheese3@gmail.com");
        Select roleSelect = new Select(driver.findElement(By.name("role")));
        roleSelect.selectByValue("STUDENT");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-info"))).click();
        WebElement passwordError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Password must be at least 6 digits')]")));
        Assert.assertTrue(passwordError.isDisplayed(), "Failed to navigate cheese1 page!");

        // input trùng email
        driver.findElement(By.name("username")).sendKeys("cheese2");
        driver.findElement(By.name("password")).sendKeys("az123456");
        driver.findElement(By.name("phone")).sendKeys("987653210");
        driver.findElement(By.name("email")).sendKeys("student7@example.com");
        Select roleSelect1 = new Select(driver.findElement(By.name("role")));
        roleSelect1.selectByValue("STUDENT");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-info"))).click();
        WebElement emailError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Email has already exists: student7@example.com')]")));
        Assert.assertTrue(emailError.isDisplayed(), "Failed to navigate cheese1 page!");

        // input trùng phone
        driver.findElement(By.name("username")).sendKeys("cheese2");
        driver.findElement(By.name("password")).sendKeys("az123456");
        driver.findElement(By.name("phone")).sendKeys("555666777");
        driver.findElement(By.name("email")).sendKeys("cheese3@gmail.com");
        Select roleSelect2 = new Select(driver.findElement(By.name("role")));
        roleSelect2.selectByValue("STUDENT");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-info"))).click();
        WebElement phoneError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Phone number has already exists: 555666777')]")));
        Assert.assertTrue(phoneError.isDisplayed(), "Failed to navigate cheese1 page!");

        // input thành công
        driver.findElement(By.name("username")).sendKeys("cheese5");
        driver.findElement(By.name("password")).sendKeys("az123456");
        driver.findElement(By.name("phone")).sendKeys("5556667778");
        driver.findElement(By.name("email")).sendKeys("cheese5@gmail.com");
        Select roleSelect3 = new Select(driver.findElement(By.name("role")));
        roleSelect3.selectByValue("STUDENT");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-info"))).click();
        WebElement successNoti = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'User has created successfully!')]")));
        Assert.assertTrue(successNoti.isDisplayed(), "Failed to navigate cheese1 page!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Đóng trình duyệt sau khi test xong
        }
    }
}
