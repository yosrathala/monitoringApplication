package com.mycompany.myapp.scrappingDeamon;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
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
public class LinkedScappingHandler extends SearchScrappingHandler {


    @Override
    public ResultatRecherche getResult(JobConfig jobConfig) {
    	System.out.println("=============================== Start Scrapping LinkedIn =============================== ");
        ResultatRecherche resultatRecherche=new ResultatRecherche();
        Set<ResultatItem> resultatItems=new HashSet<>();
        
        Motcle motcle = jobConfig.getMotcle();
        String login = jobConfig.getSourceLogin();
        String pass= jobConfig.getSourcePassword();

        Connection.Response iniResponse=null;
        Connection.Response response=null;
        String data = null;
        String description="";
        String idR="";
        String title="";
        String datePub="";
        String url="";

        try {

            iniResponse = Jsoup.connect("https://www.linkedin.com/uas/login?goback=&trk=hb_signin").method(Connection.Method.GET).execute();

            Document responseDocument = iniResponse.parse();
            Element loginCsrfParam = responseDocument.select("input[name=loginCsrfParam]").first();

            iniResponse = Jsoup.connect("https://www.linkedin.com/uas/login-submit").cookies(iniResponse.cookies())
                .data("loginCsrfParam", loginCsrfParam.attr("value")).data("session_key", login)
                .data("session_password", pass).method(Connection.Method.POST).followRedirects(true).execute();

            String searchLink = "https://www.linkedin.com/search/results/content/?allowUnsupportedBrowser=true&keywords="+motcle.getMotinclue()+"&origin=SORT_RESULTS&facetSortBy=date_posted";
			response = Jsoup.connect(searchLink).cookies(iniResponse.cookies())
                .userAgent(
                    "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                .maxBodySize(0).method(Connection.Method.GET).execute();

            Document doc = response.parse();
            Elements links = doc.select("code");
			for (int i = 0; i < links.size(); i++) {
				if (links.get(i).toString().contains("commentary")) {
					Element element = links.get(i);
					Node node = element.childNodes().get(0);
					data = node.toString();
					i = links.size();
				}
			}

            if (data != null) {
                JSONParser jsonParser = new JSONParser();

                Object obj = jsonParser.parse(data);

                JSONObject jsonObject = (JSONObject) obj;
                JSONArray content = (JSONArray) jsonObject.get("included");
                for (int i = 0; i < content.size(); i++) {
                    JSONObject w = (JSONObject) content.get(i);
                    if (w.containsKey("commentary")) {

                        JSONObject cont3 = (JSONObject) w.get("commentary");
                        if(cont3!=null)
                        {
                            JSONObject cont4 = (JSONObject) cont3.get("text");
                            String cont5 = (String) cont4.get("text");
                            if(!cont5.contains(motcle.getMotexclue()))
                            {
                                description = cont5;
                                //System.out.println(description);

                                cont3 = (JSONObject) w.get("actor");
                                cont4 = (JSONObject) cont3.get("name");
                                title = (String) cont4.get("text");
                                //System.out.println(title);

                                cont3 = (JSONObject) w.get("actor");
                                cont4 = (JSONObject) cont3.get("subDescription");
                                cont5 = (String) cont4.get("text");
                                datePub = cont5.replaceAll("&nbsp;", "");

                                cont3 = (JSONObject) w.get("actor");
                                cont5 = (String) cont3.get("urn");
                                int idLength = cont5.split(":").length;
                                idR = cont5.split(":")[idLength-1];

                                JSONObject updateMetadata = (JSONObject) w.get("updateMetadata");
                                JSONArray actions = (JSONArray) updateMetadata.get("actions");
                                JSONObject col = (JSONObject) actions.get(0);
                                url=(String) col.get("url");
                                
                                ResultatItem resultatItem=new ResultatItem();
                                resultatItem.setPostId(idR);
                                resultatItem.setTitre(title);
                                resultatItem.setContenu(description);
                                resultatItem.setUrl(url);
                                resultatItem.setDate(datePub);
                                if(! "".equals(title) && ! "".equals(description)) {
                                	System.out.println("Found on LinkeIn ---------> " + title);
                        			resultatItems.add(resultatItem);
                        		}

                               // i = content.size();
                            }
                        }
                    }

                }

            }
            
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (HttpStatusException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        resultatRecherche.setResultatItems(resultatItems);
		resultatRecherche.setDate(ZonedDateTime.now());

		System.out.println("=============================== End Scrapping Linkedin =============================== ");
        return resultatRecherche;
    }

}
