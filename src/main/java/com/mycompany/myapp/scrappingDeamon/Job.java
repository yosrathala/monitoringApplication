package com.mycompany.myapp.scrappingDeamon;

import java.util.List;

import com.mycompany.myapp.domain.ResultatRecherche;

public class Job implements Runnable {

    SearchScrappingHandler searchHandler;
    List<NotificationHandler> notifications;
    SearchRresultHandler searchRresultHandler;

    public Job(SearchScrappingHandler searchHandler, List<NotificationHandler> notifications,
               SearchRresultHandler searchRresultHandler) {
        super();
        this.searchHandler = searchHandler;
        this.notifications = notifications;
        this.searchRresultHandler = searchRresultHandler;
    }

    public SearchScrappingHandler getSearchHandler() {
        return searchHandler;
    }

    public void setSearchHandler(SearchScrappingHandler searchHandler) {
        this.searchHandler = searchHandler;
    }

    public List<NotificationHandler> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationHandler> notifications) {
        this.notifications = notifications;
    }

    public SearchRresultHandler getSearchRresultHandler() {
        return searchRresultHandler;
    }

    public void setSearchRresultHandler(SearchRresultHandler searchRresultHandler) {
        this.searchRresultHandler = searchRresultHandler;
    }

    // public abstract Job build();
    @Override
    public void run() {
        // TODO Auto-generated method stub
        ResultatRecherche result = searchHandler.getResult();
        searchRresultHandler.save(result);
        for (NotificationHandler notificationHandler : notifications) {
            // notificationHandler.send(result);
        }

    }

}
