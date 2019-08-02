package com.mycompany.myapp.scrappingDeamon;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.mycompany.myapp.domain.Motcle;
import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.domain.Source;

@Service
public class RssScrappingHandler extends SearchScrappingHandler {

	@Override
	public ResultatRecherche getResult(Recherche search) {
		ResultatRecherche resultatRecherche = new ResultatRecherche();
		Set<ResultatItem> resultatItems = new HashSet<>();
		ResultatItem resultatItem = new ResultatItem();
		Set<Source> sources = search.getSources();
		Motcle motcle = search.getMotcle();

		String description = "";
		String titre = "";
		String acteur = "";
		String datePub = "";
		String idR = "";

		String inputsrc = "";
		String line = "";
		String xml = "";
		String url = "";

		for (Source src : sources) {
			if (src.equals("rss")) {
				try {
					URL rssURL = new URL(src.getUrl());
					BufferedReader in = new BufferedReader(new InputStreamReader(rssURL.openStream()));

					while ((line = in.readLine()) != null) {
						xml += line;
					}

					inputsrc = prettyPrintXml(xml);
					Reader inputString = new StringReader(inputsrc);
					BufferedReader reader = new BufferedReader(inputString);
					int first = 0;
					int last = 0;
					String temp = "";
					while ((line = reader.readLine()) != null) {
						if (line.contains("<title>")) {
							first = line.indexOf("<title>");
							temp = line.substring(first);
							temp = temp.replace("<title>", "");
							last = temp.indexOf("</title>");
							temp = temp.substring(0, last);
							temp = temp.replaceAll("&lt;p&gt;", "");
							temp = temp.replaceAll("&lt;/p&gt;", "");
						}

						if (temp.contains(motcle.getMotinclue()) && !temp.contains(motcle.getMotexclue())) {
							titre = temp;
							if (line.contains("<link>")) {
								int firstl = line.indexOf("<link>");
								String templ = line.substring(firstl);
								templ = templ.replace("<link>", "");
								int lastl = templ.indexOf("</link>");
								templ = templ.substring(0, lastl);
								templ = templ.replaceAll("&lt;p&gt;", "");
								templ = templ.replaceAll("&lt;/p&gt;", "");
								url = templ;
							}
							if (line.contains("<description>")) {
								int firstD = line.indexOf("<description>");
								String tempD = line.substring(firstD);
								tempD = tempD.replace("<description>", "");
								int lastD = tempD.indexOf("</description>");
								tempD = tempD.substring(0, lastD);
								tempD = tempD.replaceAll("&lt;p&gt;", "");
								tempD = tempD.replaceAll("&lt;/p&gt;", "");
								description = tempD;
							}
							if (line.contains("<pubDate>")) {
								first = line.indexOf("<pubDate>");
								temp = line.substring(first);
								temp = temp.replace("<pubDate>", "");
								last = temp.indexOf("</pubDate>");
								temp = temp.substring(0, last);
								temp = temp.replaceAll("&lt;p&gt;", "");
								temp = temp.replaceAll("&lt;/p&gt;", "");
								datePub = temp;
							}
							if (line.contains("<guid isPermaLink=\"false\">")) {
								int firsti = line.indexOf("<guid isPermaLink=\"false\">");
								String tempi = line.substring(firsti);
								tempi = tempi.replace("<guid isPermaLink=\"false\">", "");
								int lasti = tempi.indexOf("</guid>");
								tempi = tempi.substring(0, lasti);
								tempi = tempi.replaceAll("&lt;p&gt;", "");
								tempi = tempi.replaceAll("&lt;/p&gt;", "");
								idR = tempi;
							}
							resultatItem.setIdr(idR);
							resultatItem.setContenu(description);
							resultatItem.setDate(datePub);
							resultatItem.setTitre(titre);
							resultatItem.setUrl(url);
							resultatItems.add(resultatItem);

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
		}
		
		resultatRecherche.setResultatItems(resultatItems);
		resultatRecherche.setDate(ZonedDateTime.now());
		resultatRecherche.setRecherche(search);
		return resultatRecherche;
	}

	public static String prettyPrintXml(String sourceXml) {
		try {
			Transformer serializer = SAXTransformerFactory.newInstance().newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			javax.xml.transform.Source xmlSource = new SAXSource(
					new InputSource(new ByteArrayInputStream(sourceXml.getBytes())));
			StreamResult res = new StreamResult(new ByteArrayOutputStream());
			serializer.transform(xmlSource, res);
			return new String(((ByteArrayOutputStream) res.getOutputStream()).toByteArray());
		} catch (Exception e) {
			return sourceXml;
		}
	}
}
