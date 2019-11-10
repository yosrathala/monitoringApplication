package com.mycompany.myapp.scrappingDeamon;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.MailService;

@Service
public class MailNotification extends NotificationHandler {

	@Autowired
	MailService emailSender;
	@Autowired
	UserRepository userRepository;

	@Override
	void send(Set<ResultatItem> newItems, JobConfig jobConfig) {

		StringBuilder content = new StringBuilder();
		for (ResultatItem resultatItem : newItems) {
			content.append("<b>" + resultatItem.getTitre() +"</b>");
			content.append("<br>");
			content.append(resultatItem.getContenu());
			content.append("<br>");
			content.append(resultatItem.getUrl());
			content.append("<br><br>");
		}
		List<User> lstuser = userRepository.findAll();

		for (int j = 0; j < lstuser.size(); j++) {

			emailSender.sendEmail(lstuser.get(j).getEmail(),
					"Nouveau scrapping sur " + jobConfig.getSourceName() + " : " + new Date(), content.toString(),
					false, true);

		}

		/*
		 * MimeMessage mimeMessage = emailSender.createMimeMessage();
		 * 
		 * MimeMessageHelper message; try { message = new MimeMessageHelper(mimeMessage,
		 * false, StandardCharsets.UTF_8.name()); List<User> lstuser =
		 * userRepository.findAll();
		 * 
		 * for (int j = 0; j < lstuser.size(); j++) {
		 * 
		 * message.setTo(lstuser.get(j).getEmail());
		 * message.setSubject("Nouveau scrapping sur " + jobConfig.getSourceName() +
		 * " : " + new Date()); message.setText(content.toString(), true);
		 * this.emailSender.send(mimeMessage);
		 * 
		 * }
		 * 
		 * 
		 * } catch (MessagingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

}
