package com.donte.aluraflix.event.listener;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.donte.aluraflix.event.EnviarEmailEvent;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Component
public class EnviarEmailListener implements ApplicationListener<EnviarEmailEvent>{

	@Autowired
	private Configuration config;

	@Autowired
	private JavaMailSender sender;

	@Override
	public void onApplicationEvent(EnviarEmailEvent event) {
		try {
			sendEmail(event);
		} catch (MessagingException | IOException | TemplateException e) {
			System.err.println(e.getMessage());
		}
	}

	public void sendEmail(EnviarEmailEvent event) throws MessagingException, IOException, TemplateException {
		MimeMessage mimeMessage = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		String[] recips = event.getRecips().toArray(new String[0]);
		
		helper.setSubject(event.getSubject());
		helper.setTo(recips);  
		helper.setText(getEmailContent(event.getModel(), event.getTemplate()), true);
		sender.send(mimeMessage);
	}
	
    private String getEmailContent(Map<String, Object> model, String template) throws IOException, TemplateException {    	
        StringWriter stringWriter = new StringWriter();       
        config.getTemplate(template).process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

	public void sendSimpleEmail() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("donte.master@gmail.com", "master.donte@hotmail.com");
		msg.setSubject("Testing from Spring Boot");
		msg.setText("Hello World \n Spring Boot Email");
		sender.send(msg);
	}

	public void sendEmailWithAttachment() throws MessagingException, IOException {
		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true); // true = multipart message
		helper.setTo("donte.master@gmail.com");
		helper.setSubject("Testing from Spring Boot");
		helper.setText("<h1>Check attachment for image!</h1>", true);// true = text/html ; helper.setText("Check attachment for image!");  // default = text/plain
		helper.addAttachment("my_photo.png", new ClassPathResource("boobs.jpg"));
		sender.send(msg);
	}

}
