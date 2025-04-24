package com.test.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class test3 {

    @Test
    public void getUser() throws InterruptedException {
        WebDriver driver ;
        System.setProperty("webdriver.edge.driver","/Users/atr50/Downloads/edgedriver_mac64(1)/msedgedriver");
        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--headless");
//        options.setHeadless(true);
        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6000));
        driver.manage().window().maximize() ;
        driver.get("https://in.mail.yahoo.com/d/folders/1");
        while (true) {
            RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
            requestSpecBuilder.setBaseUri("https://www.naukri.com");
            Map<String, String> h = new HashMap<>();
            h.put("Authorization", "Bearer eyJraWQiOiIyIiwidHlwIjoiSldUIiwiYWxnIjoiUlM1MTIifQ.eyJkZXZpY2VUeXBlIjoiZDNza3QwcCIsInVkX3Jlc0lkIjoxMzQ2NjAxODMsInN1YiI6IjEzMTIzNTUxNyIsInVkX3VzZXJuYW1lIjoiYWJoaXNoZWt0aXdhcmkyazRAZ21haWwuY29tIiwidWRfaXNFbWFpbCI6dHJ1ZSwiaXNzIjoiSW5mb0VkZ2UgSW5kaWEgUHZ0LiBMdGQuIiwidXNlckFnZW50IjoiTW96aWxsYS81LjAgKE1hY2ludG9zaDsgSW50ZWwgTWFjIE9TIFggMTBfMTVfNykgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzEzMi4wLjAuMCBTYWZhcmkvNTM3LjM2IEVkZy8xMzIuMC4wLjAiLCJpcEFkcmVzcyI6IjkyLjIzNC4yNDguMjIxIiwidWRfaXNUZWNoT3BzTG9naW4iOmZhbHNlLCJ1c2VySWQiOjEzMTIzNTUxNywic3ViVXNlclR5cGUiOiIiLCJ1c2VyU3RhdGUiOiJBVVRIRU5USUNBVEVEIiwidWRfaXNQYWlkQ2xpZW50IjpmYWxzZSwidWRfZW1haWxWZXJpZmllZCI6ZmFsc2UsInVzZXJUeXBlIjoiam9ic2Vla2VyIiwic2Vzc2lvblN0YXRUaW1lIjoiMjAyNS0wMi0xOFQxNTozMDo0MyIsInVkX2VtYWlsIjoiYWJoaXNoZWt0aXdhcmkyazRAZ21haWwuY29tIiwidXNlclJvbGUiOiJ1c2VyIiwiZXhwIjoxNzQ1MzA3MjkzLCJ0b2tlblR5cGUiOiJhY2Nlc3NUb2tlbiIsImlhdCI6MTc0NTMwMzY5MywianRpIjoiNzY4YTA4ZDRmZjllNDQ2MDkyNzkzYWEyOWFiZTczMzgiLCJwb2RJZCI6InByb2QtY2Q1Zjk5NTZkLWhiN2d0In0.Jdu0pkm5ZQeoersK0iXBKK32GFHdMyY8O-hkPzZkNhOubrYVJd1dAqUjdeRIO_PIvAZyBlhJTjQR_phCjrLsv6F7Oddxk4pwyMdSvNXxVKPUHKnggYIEHcPyi3t_IDaWDlX0dThyLzBRH6CoeRNmkna577Cqb_uB-IvlAsUL5zsTBFLJJaR2odZ79wzKDiD_GPWmMDgPP0Q4rFJ_-XrXqWSPFPgHJf-nW4HE45fJ4L908oUc2kOzMJTbXZd5F7i4WschKKLRVTvPCv_PzBY3rKMBc2gqFm5J4KiAFW2fSkPq51QhbcFFTsR-zmGDxEkOVoHnttbxIYA-L5cWXRir2w");
            h.put("Appid", "105");
            h.put("Systemid", "Naukri");
            h.put("x-http-method-override", "PUT");
            requestSpecBuilder.addHeaders(h);
            RequestSpecification request = requestSpecBuilder.build();
            request.basePath("/cloudgateway-mynaukri/resman-aggregator-services/v1/users/self/fullprofiles");
            String body = "{\"profile\":{\"resumeHeadline\":\"Automation Lead with 9.8 years of experience | Ex Paytm| Ex Oracle\"},\"profileId\":\"8f6f279ebf9f2eab44af051a8bcedbd25ad96d27d203cae228799783fe8d87f6\"}";
            request.body(body);
            request.contentType("application/json");
            Response response = given().spec(request).when().log().all().post().then().extract().response();
            System.out.println(response.getStatusCode());
            Assert.assertEquals(response.getStatusCode(), 200);
            response.prettyPrint();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6000));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/d/compose/']")));
            WebElement compose = driver.findElement(By.xpath("//a[@href='/d/compose/']"));
            compose.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message-to-field")));
            WebElement to = driver.findElement(By.id("message-to-field"));
            to.sendKeys("goodlucktoday@gnttv.com");
            WebElement subject = driver.findElement(By.id("compose-subject-input"));
            subject.sendKeys("Good Luck Today - Shailendre ji ke Liye Sawaal");
            WebElement text = driver.findElement(By.xpath("//div[@aria-label='Message body']"));
            text.sendKeys("Namaskar Shailendri Ji,\n" +
                    "\n" +
                    "Mera naam Ashish Tewari hai, mein Bangalore se sawaal puch raha hun.\n" +
                    "Mein apne pitaji Rajkumar Tiwari ke baare mein sawaal puch raha hun, unko apni birth details yaad nahi hain isliye mene apni birth details niche mention ki hain.\n" +
                    "\n" +
                    "Name - Ashish Tewari\n" +
                    "Place of Birth - Lucknow\n" +
                    "Date of Birth - 17 August 1990\n" +
                    "Time of Birth - 9:29 AM\n" +
                    "\n" +
                    "Mera sawaal ye hai ki mere pitaji ki health kaafi kharab rehti hai. Unko stomach ki aur skin ki kaafi dikkat rehti hai. Unki age bhi kaafi ho gayi hai isliye health ki dikkat rehti hai.\n" +
                    "\n" +
                    "Kripya koi upaay batayein jisse mere pitaji ki health thik rahe aur health ki dikkate na aayein.\n" +
                    "\n" +
                    "Dhanyawaad\n" +
                    "Ashish Tewari");
            WebElement send = driver.findElement(By.xpath("//button[@title='Send this email']"));
            send.click();
            Thread.sleep(300000);
        }
    }
}
