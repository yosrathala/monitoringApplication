package com.mycompany.myapp.scrappingDeamon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.mycompany.myapp.domain.Motcle;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.domain.Source;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
@RestController
public class ProgolinScrapingHandler {
   static final String baseUrl = "https://www.progonline.com/";
    static final String username = "yosraa";
    static final String password = "yosra1234yosra";


    static final String chromeDriverPath = "C:/Users/Mariem/chromedriver_win32/chromedriver.exe";
    static final int MAX_PAGE = 20;

  public  List<ProgonlineFN> projectList = new ArrayList<ProgonlineFN>();

    public void login(String username,String password,String baseUrl ) {
         String loginUrl = String.format("%s%s", baseUrl, "/visitor_mypage.php?quoi=deconnexion");

            try {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors", "--silent");
            WebDriver driver = new ChromeDriver(options);

            System.out.println(String.format("Login with username: %s password: %s", username, password));
            driver.get(loginUrl);

            driver.findElement(By.id("login-username")).sendKeys(username);
            driver.findElement(By.id("login-password")).sendKeys(password);
            driver.findElement(By.xpath("//form//input[@type='submit']")).click();


            if (driver.findElement(By.xpath("//a[@href='mypage.php?quoi=deconnect']")) == null) {
                System.err.println("Incorrect username/password");
                System.exit(0);
            } else {
                System.out.println("Successfuly logged in!");
            }
            int currentPage = 1;
            driver.get("https://www.progonline.com/ajax/projets_disponibles.php?liste_projets=liste_all&page=" + currentPage);
            do {
                System.out.println("Processing page " + currentPage);

                List<WebElement> projects = driver.findElements(By.xpath("//ul[contains(@class,'nav-stacked')]/li"));

                // a bit dirty but because there isn't really any markup, we need to make a lot of splits !
                for (WebElement project : projects) {

                    String title = project.findElement(By.xpath(".//h3")).getText();
                    String url = project.findElement(By.xpath("./a")).getAttribute("href");
                    int id = Integer.parseInt(url.split("&id=")[1]);
                    String description = "";
                    try {
                        description = project.findElement(By.xpath("./a")).getText().split("\n\n")[0]
                            .split("Brève description du projet :")[1];
                    } catch (Exception e) {
                        // somestimes no description
                        description = "";
                    }
                    String projetOptions = "";

                    try {
                        projetOptions = project.findElement(By.xpath("./a")).getText().split("\n\n")[1];
                    } catch (Exception e) {
                        projetOptions = project.findElement(By.xpath("./a")).getText();
                    }
                    String budget = projetOptions.split("Budget : ")[1].split("\n")[0];
                    String date = projetOptions.split("Publié le : ")[1].split("\n")[0];
                    String concurrence = projetOptions.split("Concurrence : ")[1].split("\n")[0];
                    String typeClient = projetOptions.split("Type client : ")[1].split("\n")[0];
                    int nombreOffre = Integer.parseInt(projetOptions.split("Nombre d'offres : ")[1].split("\n")[0]);
                    String statistiques = projetOptions.split("Statistiques client : ")[1].split("\n")[0];
                    String pertinence = projetOptions.split("Pertinence : ")[1].split("\n")[0];

                    ProgonlineFN p = new ProgonlineFN(title, id, description, url, date, budget, pertinence, nombreOffre, statistiques, typeClient, concurrence);
                    projectList.add(p);

                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(p);
                    System.out.println(jsonString);

                }

                currentPage += 1;
                driver.get("https://www.progonline.com/ajax/projets_disponibles.php?liste_projets=liste_all&page=" + currentPage);

            } while (currentPage < MAX_PAGE + 1);


            driver.close();
            driver.quit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

