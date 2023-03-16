package com.misctest;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class sendResultByEmailTest {

    public static void main(String[] args) {
//        spring.mail.host=smtp.gmail.com
//        spring.mail.port=587
//        spring.mail.username=example@gmail.com
//                spring.mail.password= Your Generated App Password (not your Gmail Account Password)
//        spring.mail.properties.mail.smtp.auth=true
//        spring.mail.properties.mail.smtp.starttls.enable=true

        // Create object of Property file
        Properties props = new Properties();

        // this will set host of server- you can change based on your requirement
        props.put("mail.smtp.host", "smtp.gmail.com");

        // set the port of SMTP server
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        // set socket factory
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

        // set the authentication to true
//        props.put("mail.ssl.auth", "true");
        props.put("mail.smtp.auth", "true");


        // This will handle the complete authentication
        Session session = Session.getDefaultInstance(props,

                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication("wfhuser2@gmail.com", "zyexykmcplgxfkvk");

                    }

                });
        try {

            // Create object of MimeMessage class
            Message message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress("wfhuser2@gmail.com"));

            // Set the recipient address
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("wfhuser1@gmail.com,akshaayu.sup@gmail.com"));

            // Add the subject link
            message.setSubject("***** UIAutomation Test Report executed at ************");

            // Create object to add multimedia type content
            BodyPart messageBodyPart1 = new MimeBodyPart();

            String sMessage = "***Sent By Selenium Automation Script, please donot reply to this****";
            // Set the body of email
            messageBodyPart1.setText("This is message body");

            // Create another object to add another content
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            // Mention the file which you want to send
            String filename = "D:\\smplfw\\test-output\\emailable-report.html";

            // Create data source and pass the filename
            DataSource source = new FileDataSource(filename);

            // set the handler
            messageBodyPart2.setDataHandler(new DataHandler(source));

            // set the file
            messageBodyPart2.setFileName(filename);

            // Create object of MimeMultipart class
            Multipart multipart = new MimeMultipart();

            // add body part 1
            multipart.addBodyPart(messageBodyPart2);

            // add body part 2
            multipart.addBodyPart(messageBodyPart1);

            // set the content
            message.setContent(multipart);

            // finally send the email
            Transport.send(message);
//
            System.out.println("=====Email Sent=====");

        } catch (MessagingException e) {

            throw new RuntimeException(e);

        }

    }

}
