package com.mycompany.myapp;


import com.mycompany.myapp.config.ApplicationProperties;
import com.mycompany.myapp.config.DefaultProfileUtil;
import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.scrappingDeamon.*;
import io.github.jhipster.config.JHipsterConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class ProjetApp {

    private static final Logger log = LoggerFactory.getLogger(ProjetApp.class);

    private final Environment env;

    public ProjetApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes projet.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProjetApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
        Recherche r =new Recherche();
        Motcle motcle=new Motcle();
        motcle.setMotinclue("projet");
        motcle.setMotexclue("youtube");

        Source source=new Source();
        Set<Source> sourceSet=new HashSet<>();
        //ProgonlineScrappingHandler pr=new ProgonlineScrappingHandler();
       // pr.prog();

        //****************Test Flux RSS Scrapping****************

       /* source.setNom("france24");
        source.setUrl("https://www.france24.com/fr/rss");
        sourceSet.add(source);
        r.setMotcle(motcle);
        r.setSources(sourceSet);
        RssScrappingHandler rss=new RssScrappingHandler(r);
        ResultatRecherche resultatRecherche=rss.getResult();*/


        //****************Test LinkedIn Scrapping****************

       /* source.setLogin("benamar.mustafa@gmail.com");
        source.setMotPasse("*******");
        source.setNom("linkedin");
        source.setUrl("https://www.linkedin.com");
        sourceSet.add(source);
        r.setMotcle(motcle);
        r.setSources(sourceSet);
        LinkedScappingHandler lk=new LinkedScappingHandler(r);
        ResultatRecherche resultatRecherche=lk.getResult();*/

        //****************Test Facebook Scrapping****************

       /* source.setLogin("mariemfrikha82@gmail.com");
        source.setMotPasse("*****");
        source.setNom("facebook");
        source.setUrl("https://www.facebook.com");
        sourceSet.add(source);
        r.setMotcle(motcle);
        r.setSources(sourceSet);
        FacebookScrappingHandler fb=new FacebookScrappingHandler(r);
        ResultatRecherche resultatRecherche=fb.getResult();*/


        //****************Test Progolin Scrapping****************

       source.setLogin("yosraa");
        source.setMotPasse("yosra1234yosra");
        source.setNom("progonline");
        source.setUrl("https://www.progonline.com/");
        sourceSet.add(source);
        r.setMotcle(motcle);
        r.setSources(sourceSet);
        ProgonlineScrappingHandler pr=new ProgonlineScrappingHandler(r);
        ResultatRecherche resultatRecherche=pr.getResult();
        System.out.println(resultatRecherche.getResultatItems().size());

        for(ResultatItem res : resultatRecherche.getResultatItems())
        {
            System.out.println("Id "+res.getIdr());
            System.out.println("Title "+res.getTitre());
            System.out.println("Description "+res.getContenu());
        }
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles());
    }

}
