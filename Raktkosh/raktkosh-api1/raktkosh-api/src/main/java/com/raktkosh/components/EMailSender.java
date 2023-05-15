package com.raktkosh.components;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EMailSender {

  @Autowired
  private JavaMailSender emailSender;
  
  @Value("${com.raktkosh.MAIL_LINK}")
  private String origin;

  public void sendSimpleMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    emailSender.send(message);
  }

  public void sendVerificationEmail(String to, String token) throws MessagingException {
	//create link with token to verify
    String url = origin+"/verify/"+token;
    //Multipurpose Internet Mail Extension (MIME) Protocol 
    MimeMessage helper = emailSender.createMimeMessage();
    // It is the helper class for creating a MIME message. It offers support for inline elements such as images, typical mail attachments and HTML text content.
    MimeMessageHelper template = new MimeMessageHelper(helper, true);
    template.setTo(to);
    // Set the subject of the message
    template.setSubject("Raktkosh Account Verification");
    // bulding message for mail 
    StringBuilder message = new StringBuilder("<h1>Raktkosh account verification.</h1>");
    message.append("<p style='crimson'>An account has been created using this email address <b>" + to + "</b></p>");
    message.append("<p>If the request has been made by you, follow the link to verify.</p>");
    message.append("<p><a href='"+url+"'>"+url+"</a></p>");
    message.append("<p><b>You can simply ignnore this, if you've not requested.<b></p>");
    // attaching message to mail 
    template.setText(message.toString(), true);
    // Send the given mail message
    emailSender.send(helper);
  }

public void resetPasswordEmail(String email,int otp) throws MessagingException {

	String url = origin+"/resetPassword/";
	 MimeMessage helper = emailSender.createMimeMessage();
	    MimeMessageHelper template = new MimeMessageHelper(helper, true);
	    template.setTo(email);
	    template.setSubject("Raktkosh Account Password Reset Mail");
	    StringBuilder message = new StringBuilder("<h1>Raktkosh account verification.</h1>");
	    message.append("<p style='crimson'>To change your password Enter the following otp <b>" + otp + "</b></p>");
	    message.append("<p><b>You can simply ignnore this, if you've not requested.<b></p>");
	    template.setText(message.toString(), true);
	    emailSender.send(helper);
	
}

 
}
