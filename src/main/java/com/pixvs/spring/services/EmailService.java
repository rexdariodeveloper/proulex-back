package com.pixvs.spring.services;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by David Arroyo
 */
@Service
public class EmailService{

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendSimpleMessageUsingTemplate(String to,
                                               String subject,
                                               SimpleMailMessage template,
                                               String... templateArgs) {
        String text = String.format(template.getText(), templateArgs);
        sendSimpleMessage(to, subject, text);
    }

    @Async
    public void sendSimpleMessage(String to, String subject, String text) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(environment.getProperty("enviroment.mail.from"));
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
            System.out.println("Correo enviado con éxito");
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    public void sendMessageWithAttachment(String to,
                                          String subject,
                                          String text,
                                          String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            message.setFrom(environment.getProperty("enviroment.mail.from"));

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithAttachment(String to,
                                          String subject,
                                          String text,
                                          String nombreArchivo,
                                          ByteArrayInputStream inputStream) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment(nombreArchivo,
                    new ByteArrayResource(IOUtils.toByteArray(inputStream)));
            message.setFrom(environment.getProperty("enviroment.mail.from"));


            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageWithAttachment(String to,
                                          String subject,
                                          String text,
                                          HashMap<String, InputStream> files) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(environment.getProperty("enviroment.mail.from"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            if (files != null) {
                for (Map.Entry<String, InputStream> entry : files.entrySet()) {
                    helper.addAttachment(entry.getKey(), new ByteArrayResource(IOUtils.toByteArray(entry.getValue())));
                }
            }

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "This is the test email template for your email:\n%s\n");
        return message;
    }

    @Async
    public void sendMessageWithHtml(String[] to, String subject, String templateName, Context params) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            String html = templateEngine.process(templateName, params);
            helper.setTo(to);
            helper.setText(html, true);
            helper.setSubject(subject);
            helper.setFrom(environment.getProperty("enviroment.mail.from"));
            emailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
