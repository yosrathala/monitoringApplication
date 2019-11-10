package com.mycompany.myapp.scrappingDeamon;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.ResultatRecherche;

@Service
public class SmsNotification extends NotificationHandler{

	@Override
	void send(Set<ResultatItem> newItems, JobConfig jobConfig) {
		// TODO Auto-generated method stub
		
	}
}
