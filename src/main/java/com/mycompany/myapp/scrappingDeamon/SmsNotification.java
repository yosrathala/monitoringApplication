package com.mycompany.myapp.scrappingDeamon;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.ResultatRecherche;

@Service
public class SmsNotification extends NotificationHandler{

	@Override
	void send(ResultatRecherche result) {
		System.out.println("Sending sms ...");
		
	}



}
