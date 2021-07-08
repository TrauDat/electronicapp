package com.elec.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.elec.bean.Mail;
import com.elec.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender mailSender;
	
	@Override
	public void sendMail(Mail mail) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			
			mimeMessageHelper.setSubject(mail.getMailSubject());
			mimeMessageHelper.setFrom( new InternetAddress(mail.getMailFrom()));
			mimeMessageHelper.setTo(mail.getMailTo());
			mimeMessageHelper.setText(mail.getMailContent());
			
			mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch(MessagingException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
