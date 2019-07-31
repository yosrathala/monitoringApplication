package com.mycompany.myapp.scrappingDeamon;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.mycompany.myapp.domain.Motcle;
import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.domain.Source;

@Service
public class ProgonlineScrappingHandler  extends SearchScrappingHandler{

    static final String chromeDriverPath = "C:/Users/webdriver/chromedriver.exe" ;
    static final int MAX_PAGE = 20;
    public ResultatRecherche resultatRecherche=new ResultatRecherche();
    public ResultatItem resultatItem=new ResultatItem();
    public Set<ResultatItem> resultatItems=new HashSet<ResultatItem>();
    public List<ProgonlineFN> projectList = new ArrayList<ProgonlineFN>();

   @Override
    public ResultatRecherche getResult(Recherche search) {
       System.out.println (System.getProperty ("os .prénom"));
       System.out.println (System.getProperty ("os.version"));
       Set<Source> sources= search.getSources();
        Motcle motcle= search.getMotcle();
        String username="";
        String password="";
        String baseUrl="";
        for(Source src : sources)
        {
            if(src.getNom().contains("progonline"))
            {
                username=src.getLogin();
                password=src.getMotPasse();
                baseUrl=src.getUrl();
            }
            System.out.println("zzzz"+username+"yyyyy"+password+"rrr"+baseUrl);
        }
        String loginUrl = String.format("%s%s", baseUrl, "/visitor_mypage.php?quoi=deconnexion");

        try{
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--silent");
            WebDriver driver = new ChromeDriver(options);

            System.out.println(String.format("Login with username: %s password: %s", username, password));
            driver.get(loginUrl);

            driver.findElement(By.id("login-username")).sendKeys(username);
            driver.findElement(By.id("login-password")).sendKeys(password);
            driver.findElement(By.xpath("//form//input[@type='submit']")).click();


            if (driver.findElement(By.xpath("//a[@href='mypage.php?quoi=deconnect']")) == null){
                System.err.println("Incorrect username/password");
                System.exit(0);
            }else{
                System.out.println("Successfuly logged in!");
            }
            int currentPage = 1;
            driver.get("https://www.progonline.com/ajax/projets_disponibles.php?liste_projets=liste_all&page=" + currentPage);
            do{
                System.out.println("Processing page " + currentPage);

                List<WebElement> projects = driver.findElements(By.xpath("//ul[contains(@class,'nav-stacked')]/li"));

                // a bit dirty but because there isn't really any markup, we need to make a lot of splits !
                List<ProgonlineFN> projectList = new ArrayList<ProgonlineFN>();
                for(WebElement project : projects) {
                    System.out.println("********************************************************************** ");
                    System.out.println("********************************************************************** ");
                    String title = project.findElement(By.xpath(".//h3")).getText();
                    // String mot="Developpeur application";

                    if (title.contains(motcle.getMotinclue())){
                        System.out.println("le mot " + motcle.getMotinclue() + " est contenu dans la phrase : " + title);


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

                        ProgonlineFN p = new ProgonlineFN(title,motcle.getMotinclue(), id, description, url, date, budget, pertinence, nombreOffre, statistiques, typeClient, concurrence);

                        resultatItem.setTitre(title);
                        resultatItem.setContenu(description);
                        resultatItem.setIdr(Integer.toString(id));
                        resultatItem.setDate(date);
                        resultatItem.setUrl(url);
                        resultatItems.add(resultatItem);
                        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        System.out.println("Id "+resultatItem.getIdr());
                        System.out.println("Date "+resultatItem.getDate());

                        System.out.println("Title "+resultatItem.getTitre());
                        System.out.println("Description "+resultatItem.getContenu());
                        System.out.println("URL "+resultatItem.getUrl());
                        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        projectList.add(p);
                        ObjectMapper mapper = new ObjectMapper();
                        String jsonString = mapper.writeValueAsString(p) ;
                        System.out.println(jsonString);

                    }



                }

                currentPage += 1;
                driver.get("https://www.progonline.com/ajax/projets_disponibles.php?liste_projets=liste_all&page=" + currentPage);

            }while (currentPage < MAX_PAGE + 1  );


            driver.close();
            driver.quit();


    } catch (FailingHttpStatusCodeException e) {
        e.printStackTrace();
   /* } catch (IOException e) {
        e.printStackTrace();*/
    } catch (Exception e) {
        e.printStackTrace();
        }
        resultatRecherche.setResultatItems(resultatItems);

       return resultatRecherche;
   }

}

