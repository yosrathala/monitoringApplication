package com.mycompany.myapp.scrappingDeamon;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.Motcle;
import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.domain.Source;

@Service
public class FacebookScrappingHandler extends SearchScrappingHandler{


    @Override
    public ResultatRecherche getResult(JobConfig jobConfig) {
    	System.out.println("=============================== Start Scrapping Facebook =============================== ");
        ResultatRecherche resultatRecherche=new ResultatRecherche();
        Set<ResultatItem> resultatItems=new HashSet<>();
        
        Motcle motcle=  jobConfig.getMotcle();
        String login="";
        String pass="";
        login=jobConfig.getSourceLogin();
        pass=jobConfig.getSourcePassword();

        Response req;
        try {
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";

            req = Jsoup.connect("https://m.facebook.com/login/async/?refsrc=https%3A%2F%2Fm.facebook.com%2F&lwv=100")
                .userAgent(userAgent)
                .method(Method.POST).data("email", login).data("pass", pass)
                .followRedirects(true).execute();

            String searchink = "https://www.facebook.com/search/posts/?q="+motcle.getMotinclue()+"&epa=SERP_TAB";
			Document d = Jsoup.connect(searchink ).userAgent(userAgent)
                .cookies(req.cookies()).get();
            Elements links =d.select("code");
            for(int i=0;i<links.size();i++)
            {
                Element element = links.get(i);
                Node node = element.childNodes().get(0);
                String   str = node.toString();
                String strNew = str.substring(0, str.length()-4);
                String strNew1=strNew.substring(6);
                Document doc = Jsoup.parse(strNew1);
                Elements links1 =doc.select("div._6-cp");
                Elements links12 =doc.select("div._6-cn");
                String description="";
                String datePub="";
                String title="";
                String idR="";
                String url="https://www.facebook.com";
                if(links1.size()>0)
                {
                    Element links2 = links1.get(0);
                    Node node1 = links2.childNodes().get(0);
                    Document doc1 = Jsoup.parse(node1.toString());

                    Element links21 = links12.get(0);
                    Node node11 = links21.childNodes().get(0);
                    Document doc11 = Jsoup.parse(node11.toString());
                    title=doc11.getElementsByTag("a").text();

                    datePub=doc1.getElementsByTag("a").text();

                    int lengthId=doc1.getElementsByTag("a").attr("href").split("/").length;
                    idR=doc1.getElementsByTag("a").attr("href").split("/")[lengthId-1];
                    url+=doc1.getElementsByTag("a").attr("href");

                    doc1.select("span").remove();

                    description=doc1.getElementsByTag("div").text();

                   // if(!description.contains(motcle.getMotexclue()))
                       // i=links.size();
                                   
            		if(! "".equals(title) && ! "".equals(description)) {
            			ResultatItem resultatItem=new ResultatItem();
                        resultatItem.setPostId(idR);
                        resultatItem.setTitre(title);
                        resultatItem.setContenu(description);
                        resultatItem.setDate(datePub);
                        resultatItem.setUrl(url);
                		resultatRecherche.setDate(ZonedDateTime.now());
                		
            			System.out.println("Found on Facebook ---------> " + title);
    					resultatItems.add(resultatItem);
    				}
                }
                
            }
            System.out.println("=============================== End Scrapping Facebook =============================== ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        resultatRecherche.setResultatItems(resultatItems);

        return resultatRecherche;
    }
}
