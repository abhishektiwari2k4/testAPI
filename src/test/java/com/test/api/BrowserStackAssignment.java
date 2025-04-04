package com.test.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;

import static io.restassured.RestAssured.given;

public class BrowserStackAssignment {

    @Test
    public void browserStackAssignment(){
        WebDriver driver ;
        System.setProperty("webdriver.edge.driver","/Users/atr50/Downloads/edgedriver_mac64(1)/msedgedriver");
        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--remote-allow-origins=*");
        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
        driver.manage().window().maximize() ;
        driver.get("https://elpais.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(500));
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pmConsentWall-button")));
            WebElement acceptCookie = driver.findElement(By.className("pmConsentWall-button"));
            acceptCookie.click();
        }catch (Exception e){
            System.out.println("Cookie accept button is not present.");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='suscríbete']")));
        WebElement opinion = driver.findElement(By.xpath("//div[@class='sm _df']/a[2]"));
        opinion.click();
        List<WebElement> section = driver.findElements(By.xpath("//section[@data-dtm-region='portada_apertura']/div"));
        List<String> title = new ArrayList<>();
        for(WebElement w: section){
            List<WebElement> articles = w.findElements(By.tagName("article"));
            for (WebElement ar: articles){
                title.add(ar.findElement(By.tagName("header")).findElement(By.tagName("h2")).getText());
                System.out.println("Title is " + ar.findElement(By.tagName("header")).findElement(By.tagName("h2")).getText());
                System.out.println("Content is " + ar.findElement(By.tagName("p")).getText());
            }
            if (title.size()>=5)
                break;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key","8af4502a88msheeede4be4cfdad0p1457e7jsn3ef70b91eb29");
        for (String s: title) {
            String body = "{\"from\":\"auto\",\"to\":\"en\",\"text\":\"convert\"}";
            body = body.replace("convert", s);
            System.out.println(s);
            System.out.println(body);
            Response rs = given().contentType(ContentType.JSON).headers(headers).body(body).when().
                    post("https://google-translate113.p.rapidapi.com/api/v1/translator/text").then().extract().response();
            System.out.println(rs.jsonPath().getString("trans"));
        }
    }
}
