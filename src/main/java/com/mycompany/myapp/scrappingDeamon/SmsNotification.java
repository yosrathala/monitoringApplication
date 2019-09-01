package com.mycompany.myapp.scrappingDeamon;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;

@Service
public class SmsNotification extends NotificationHandler{

	@Override
	void send(List<ResultatItem> newItems, JobConfig jobConfig) {
		// TODO Auto-generated method stub
		
	}
}
