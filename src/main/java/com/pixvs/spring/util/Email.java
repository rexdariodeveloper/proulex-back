package com.pixvs.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static com.pixvs.spring.util.Utilidades.logFile;

/**
 * Created by PixvsChevy on 3/30/2017.
 */
@SuppressWarnings("ControlFlowStatementWithoutBraces")
@Component
public class Email {

    @Autowired
    private Environment environment;

    @Async
    public void sendEmail(String correoDestino, String asunto, String mensaje) {

        Properties props = new Properties();
        props.put("mail.smtp.user", environment.getProperty("enviroment.mail.smtp.user"));
        props.put("mail.smtp.host", environment.getProperty("enviroment.mail.smtp.host"));
        props.put("mail.smtp.port", environment.getProperty("enviroment.mail.smtp.port"));
        props.put("mail.smtp.starttls.enable", environment.getProperty("enviroment.mail.smtp.starttls.enable"));
        props.put("mail.smtp.debug", environment.getProperty("enviroment.mail.smtp.debug"));
        props.put("mail.smtp.auth", environment.getProperty("enviroment.mail.smtp.auth"));
        props.put("mail.smtp.socketFactory.port", environment.getProperty("enviroment.mail.smtp.port"));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(environment.getProperty("enviroment.mail.smtp.user"), environment.getProperty("enviroment.mail.smtp.pass"));
                    }
                });

        try {
            System.out.println("Enviando Email..");
            Thread.sleep(100);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(environment.getProperty("enviroment.mail.smtp.email")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correoDestino));
            message.setSubject(asunto);
            message.setText(mensaje);
            // message.setContent(mensaje, "text/html; charset=utf-8");

            //Transport.send(message);
            Transport transport = session.getTransport("smtps");
            transport.connect(environment.getProperty("enviroment.mail.smtp.host"), Integer.valueOf(environment.getProperty("enviroment.mail.smtp.port")), environment.getProperty("enviroment.mail.smtp.user"), environment.getProperty("enviroment.mail.smtp.pass"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("Correo Enviado");

        } catch (MessagingException e) {
            System.out.println("Problema Email: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Problema Email: " + e.getMessage());
        }

    }

    @Async
    public void sendHtmlEmail(String correoDestino, String asunto, String mensaje) {

        if (!(environment.getActiveProfiles() != null && "production" .equals(environment.getActiveProfiles()[0]))) {
            mensaje += "\n\nEste correo se debería mandar a " + correoDestino;
            correoDestino = "pixvs.server@gmail.com";
        }

        Properties props = new Properties();
        props.put("mail.smtp.user", environment.getProperty("enviroment.mail.smtp.user"));
        props.put("mail.smtp.host", environment.getProperty("enviroment.mail.smtp.host"));
        props.put("mail.smtp.port", environment.getProperty("enviroment.mail.smtp.port"));
        props.put("mail.smtp.starttls.enable", environment.getProperty("enviroment.mail.smtp.starttls.enable"));
        props.put("mail.smtp.debug", environment.getProperty("enviroment.mail.smtp.debug"));
        props.put("mail.smtp.auth", environment.getProperty("enviroment.mail.smtp.auth"));
        props.put("mail.smtp.socketFactory.port", environment.getProperty("enviroment.mail.smtp.port"));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(environment.getProperty("enviroment.mail.smtp.user"), environment.getProperty("enviroment.mail.smtp.pass"));
                    }
                });

        try {
            System.out.println("Enviando Email..");
            Thread.sleep(100);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(environment.getProperty("enviroment.mail.smtp.email")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correoDestino));
            message.setSubject(asunto);
            message.setContent(mensaje, "text/html; charset=utf-8");

            //Transport.send(message);
            Transport transport = session.getTransport("smtps");
            transport.connect(environment.getProperty("enviroment.mail.smtp.host"), Integer.valueOf(environment.getProperty("enviroment.mail.smtp.port")), environment.getProperty("enviroment.mail.smtp.user"), environment.getProperty("enviroment.mail.smtp.pass"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("Correo Enviado");

        } catch (MessagingException e) {
            System.out.println("Problema Email: " + e.getMessage());
            logFile("pixvs-email", e.getMessage(), true);
        } catch (InterruptedException e) {
            System.out.println("Problema Email: " + e.getMessage());
        }

    }
}
