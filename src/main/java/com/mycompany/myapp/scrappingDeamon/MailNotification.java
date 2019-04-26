package com.mycompany.myapp.scrappingDeamon;


public class MailNotification extends NotificationHandler{

    @Override
    String typeNotification() {
        String type = "Mail";
        return type;
    }

}
