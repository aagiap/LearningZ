package com.project.learningz;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LoginTest {
    public static void main(String[] args) {
        WebDriverManager.edgedriver().driverVersion("134.0.3124.51").setup();

        WebDriver driver = new EdgeDriver();




        try {
            // Mở trang web
            driver.get("http://localhost:8080/login");

            WebElement usernameField = driver.findElement(By.xpath("//input[@name='username']"));

            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));


            // Tìm và nhập username

            usernameField.sendKeys("teacher1");

            // Tìm và nhập password

            passwordField.sendKeys("az123456");

            // Click nút đăng nhập

            loginButton.click();

            // Kiểm tra xem đăng nhập thành công hay không
            //String expectedUrl = "http://localhost:8080/home";
            String expectedUrl = "http://localhost:8080/teacher";
            if (driver.getCurrentUrl().equals(expectedUrl)) {
                System.out.println("Đăng nhập thành công!");
            } else {
                System.out.println("Đăng nhập thất bại!");
            }
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }

}
