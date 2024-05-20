package com.CS109.game2048.util;

import java.security.SecureRandom;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
    public static String sendEmail(String to) {

        String from = "pangrz@foxmail.com";
        String host = "smtp.qq.com";
        String verificationCode = generateVerificationCode();

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        //properties.put("mail.debug", "true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "kicxkgwtkmckdjfh");
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(to)});
            message.setSubject("2048 Sign Up");
            message.setText(verificationCode);
            Transport.send(message);
            //System.out.println("success");
        } catch (MessagingException ignore) {
            //throw new RuntimeException();
            return null;
        }
        return verificationCode;
    }

    public static String generateVerificationCode() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final SecureRandom RANDOM = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }

}
