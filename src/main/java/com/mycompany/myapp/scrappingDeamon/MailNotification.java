package com.mycompany.myapp.scrappingDeamon;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.UserRepository;

@Service
public class MailNotification extends NotificationHandler {

	@Autowired
	JavaMailSender emailSender;
	@Autowired
	UserRepository userRepository;

	@Override
	void send(List<ResultatItem> newItems) {
		
		StringBuilder content = new StringBuilder();
		for (ResultatItem resultatItem : newItems) {
			content.append("<br>");
			content.append(resultatItem.getContenu());
		}
		SimpleMailMessage message = new SimpleMailMessage();
		List<User> lstuser = userRepository.findAll();
		
		for (int j = 0; j < lstuser.size(); j++) {

			message.setTo(lstuser.get(j).getEmail());
			message.setSubject("Nouveau scrapping : " + new Date());
			message.setText(content.toString());
			this.emailSender.send(message);

		}

	}

}
