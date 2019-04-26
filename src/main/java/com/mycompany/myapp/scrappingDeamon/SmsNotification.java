package com.mycompany.myapp.scrappingDeamon;

public class SmsNotification extends NotificationHandler{

    @Override
    String typeNotification() {
        String type = "SMS";
        return type;
    }

}
