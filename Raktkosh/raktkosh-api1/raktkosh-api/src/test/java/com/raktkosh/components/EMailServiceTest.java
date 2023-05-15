package com.raktkosh.components;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EMailServiceTest {

  @Autowired
  private EMailSender emailSender;
  
  @Test
  void testSendMail() throws MessagingException {
    emailSender.sendVerificationEmail("anshuman02@outlook.com", "3453fwertwer");
  }
}
