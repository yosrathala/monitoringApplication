package com.mycompany.myapp.scrappingDeamon;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.domain.Source;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;
import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;



public class RssScrappingHandler extends SearchScrappingHandler {

    public RssScrappingHandler(Recherche search) {
        super(search);
    }

    @Override
    public ResultatRecherche getResult() {
        ResultatRecherche resultatRecherche=new ResultatRecherche();
        Set<ResultatItem> resultatItems=new HashSet<>();
        ResultatItem resultatItem=new ResultatItem();
        Set<Source> sources=getSearch().getSources();
        Motcle motcle=getSearch().getMotcle();

        String description="";
        String titre="";
        String acteur="";
        String datePub="";
        String idR="";

        String inputsrc="";
        String line="";
        String xml="";

        for(Source src : sources) {
            try {
                URL rssURL = new URL(src.getUrl());
                BufferedReader in = new BufferedReader(new InputStreamReader(rssURL.openStream()));

                while ((line = in.readLine()) != null) {
                    xml += line;
                }


                inputsrc = prettyPrintXml(xml);
                Reader inputString = new StringReader(inputsrc);
                BufferedReader reader = new BufferedReader(inputString);
                int first=0;
                int last=0;
                String temp="";
                while ((line = reader.readLine()) != null) {
                    if (line.contains("<title>"))
                    {
                        first = line.indexOf("<title>");
                        temp = line.substring(first);
                        temp = temp.replace("<title>", "");
                        last = temp.indexOf("</title>");
                        temp = temp.substring(0, last);
                        temp = temp.replaceAll("&lt;p&gt;", "");
                        temp = temp.replaceAll("&lt;/p&gt;", "");
                    }

                    if(temp.contains(motcle.getMotinclue())&&!temp.contains(motcle.getMotexclue()))
                    {
                        titre = temp;
                        if (line.contains("<description>"))
                        {
                            int firstD = line.indexOf("<description>");
                            String tempD = line.substring(firstD);
                            tempD = tempD.replace("<description>", "");
                            int lastD = tempD.indexOf("</description>");
                            tempD = tempD.substring(0, lastD);
                            tempD = tempD.replaceAll("&lt;p&gt;", "");
                            tempD = tempD.replaceAll("&lt;/p&gt;", "");
                            description=tempD;
                        }
                        if (line.contains("<author>"))
                        {
                            first = line.indexOf("<author>");
                            temp = line.substring(first);
                            temp = temp.replace("<author>", "");
                            last = temp.indexOf("</author>");
                            temp = temp.substring(0, last);
                            temp = temp.replaceAll("&lt;p&gt;", "");
                            temp = temp.replaceAll("&lt;/p&gt;", "");
                            acteur=temp;
                        }
                        if (line.contains("<pubDate>"))
                        {
                            first = line.indexOf("<pubDate>");
                            temp = line.substring(first);
                            temp = temp.replace("<pubDate>", "");
                            last = temp.indexOf("</pubDate>");
                            temp = temp.substring(0, last);
                            temp = temp.replaceAll("&lt;p&gt;", "");
                            temp = temp.replaceAll("&lt;/p&gt;", "");
                            datePub=temp;
                        }
                        if (line.contains("<guid isPermaLink=\"false\">"))
                        {
                            first = line.indexOf("<guid isPermaLink=\"false\">");
                            temp = line.substring(first);
                            temp = temp.replace("<guid isPermaLink=\"false\">", "");
                            last = temp.indexOf("</guid>");
                            temp = temp.substring(0, last);
                            temp = temp.replaceAll("&lt;p&gt;", "");
                            temp = temp.replaceAll("&lt;/p&gt;", "");
                            idR=temp;
                        }
                        resultatItem.setIdr(idR);
                        resultatItem.setContenu(description);
                        resultatItem.setTitre(titre);
                    }
                }
                in.close();
                reader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        resultatItems.add(resultatItem);
        resultatRecherche.setResultatItems(resultatItems);

        return resultatRecherche;
    }

    public static String prettyPrintXml(String sourceXml) {
        try {
            Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            javax.xml.transform.Source xmlSource = new SAXSource(new InputSource(new ByteArrayInputStream(sourceXml.getBytes())));
            StreamResult res = new StreamResult(new ByteArrayOutputStream());
            serializer.transform(xmlSource, res);
            return new String(((ByteArrayOutputStream) res.getOutputStream()).toByteArray());
        } catch (Exception e) {
            return sourceXml;
        }
    }
}
