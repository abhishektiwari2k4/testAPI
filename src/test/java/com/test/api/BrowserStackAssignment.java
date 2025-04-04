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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
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
        
        // Accept the cookie popup if present
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("pmConsentWall-button")));
            WebElement acceptCookie = driver.findElement(By.className("pmConsentWall-button"));
            acceptCookie.click();
        }catch (Exception e){
            System.out.println("Cookie accept button is not present.");
        }
        
        // Wait till subscribe link is present on screen in spanish
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='suscríbete']")));
        // Click opinion
        WebElement opinion = driver.findElement(By.xpath("//div[@class='sm _df']/a[2]"));
        opinion.click();

        /**
         * Getting the first 5 titles and headers. If the article contains an image then that would be saved to local
         * Storing the title values in list to translate later
         */
        List<WebElement> section = driver.findElements(By.xpath("//section[@data-dtm-region='portada_apertura']/div"));
        List<String> title = new ArrayList<>();
        for(WebElement w: section){
            List<WebElement> articles = w.findElements(By.tagName("article"));
            for (WebElement ar: articles){
                title.add(ar.findElement(By.tagName("header")).findElement(By.tagName("h2")).getText());
                System.out.println("Title is " + ar.findElement(By.tagName("header")).findElement(By.tagName("h2")).getText());
                System.out.println("Content is " + ar.findElement(By.tagName("p")).getText());
                String titleValue = ar.findElement(By.tagName("header")).findElement(By.tagName("h2")).getText();
                try{
                    String url = ar.findElement(By.tagName("figure")).findElement(By.tagName("a")).findElement(By.tagName("img")).getAttribute("src");
                    System.out.println("Image url is " + url);
                    URL imageURL = new URL(url);
                    BufferedImage saveImage = ImageIO.read(imageURL);
                    ImageIO.write(saveImage, "jpg", new File(titleValue + ".jpg"));
                }catch (Exception e){
                    System.out.println("No Image was found.");
                }
            }
            if (title.size()>=5)
                break;
        }
        
        // Using Google Translate API to translate the titles
        Map<String, String> headers = new HashMap<>();
        headers.put("X-RapidAPI-Key","8af4502a88msheeede4be4cfdad0p1457e7jsn3ef70b91eb29");
        String h = "";
        for (String s: title) {
            String body = "{\"from\":\"auto\",\"to\":\"en\",\"text\":\"convert\"}";
            body = body.replace("convert", s);
            System.out.println(s);
            System.out.println(body);
            Response rs = given().contentType(ContentType.JSON).headers(headers).body(body).when().
                    post("https://google-translate113.p.rapidapi.com/api/v1/translator/text").then().extract().response();
            System.out.println(rs.jsonPath().getString("trans"));
            h+= rs.jsonPath().getString("trans") + " ";
        }
        //Removing special characters from the combined title values which have been stored in a string. Then checking the
        // frequency of these words.
        h = h.replace(".", "").replace(",", "").replace("‘", "")
                .replace("’", "").replace(":", "").replace("-", "").toLowerCase();
        System.out.println(h);
        String [] h1 = h.split(" ");
        Map<String, Integer> m = new HashMap<>();
        for(String s: h1){
            if(m.containsKey(s)){
                m.put(s, m.get(s)+1);
            }
            else
                m.put(s,1);
        }
        for (Map.Entry<String, Integer> m1: m.entrySet()){
            if(m1.getValue()>2){
                System.out.println(m1.getKey());
            }
        }
    }
}
