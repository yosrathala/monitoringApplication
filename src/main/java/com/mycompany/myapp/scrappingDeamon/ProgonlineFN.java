package com.mycompany.myapp.scrappingDeamon;

public class ProgonlineFN {

    private String title;
    private int id;
    private String mot;
    private String description;
    private String url;
    private String date;
    private String budget;
    private String pertinence;
    private int nombreOffre;
    private String statistiqueClient;
    private String typeClient;
    private String concurrence;

    public ProgonlineFN(String title,String mot, int id, String description, String url, String date, String budget, String pertinence,
                   int nombreOffre, String statistiqueClient, String typeClient, String concurrence) {
        super();
        this.title = title;
        this.id = id;
        this.mot=mot;
        this.description = description;
        this.url = url;
        this.date = date;
        this.budget = budget;
        this.pertinence = pertinence;
        this.nombreOffre = nombreOffre;
        this.statistiqueClient = statistiqueClient;
        this.typeClient = typeClient;
        this.concurrence = concurrence;
    }

    public String getMot() {
        return mot;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getBudget() {
        return budget;
    }
    public void setBudget(String budget) {
        this.budget = budget;
    }
    public String getPertinence() {
        return pertinence;
    }
    public void setPertinence(String pertinence) {
        this.pertinence = pertinence;
    }
    public int getNombreOffre() {
        return nombreOffre;
    }
    public void setNombreOffre(int nombreOffre) {
        this.nombreOffre = nombreOffre;
    }
    public String getStatistiqueClient() {
        return statistiqueClient;
    }
    public void setStatistiqueClient(String statistiqueClient) {
        this.statistiqueClient = statistiqueClient;
    }
    public String getTypeClient() {
        return typeClient;
    }
    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }
    public String getConcurrence() {
        return concurrence;
    }
    public void setConcurrence(String concurrence) {
        this.concurrence = concurrence;
    }






}
