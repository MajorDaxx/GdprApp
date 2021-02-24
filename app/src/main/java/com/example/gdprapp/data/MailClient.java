package com.example.gdprapp.data;


import android.util.Log;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailClient {

    public static MailClient getClient(String fromEmail, String password,Properties p){
        return new MailClient(fromEmail, password,p);
    }

    private Properties props;
    private String fromEmail;
    private String password;


    private MailClient(String host, String port, String starttls, String auth,String fromEmail,String password){
        props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable",starttls);
        props.put("mail.smtp.auth", auth);

        this.fromEmail = fromEmail;
        this.password = password;
    }

    private MailClient(String fromEmail,String password,Properties p){
        props =p;

        this.fromEmail = fromEmail;
        this.password = password;
    }


    /**
     * Rework!!!!!!!
     */
    public void sendMessage(String toEmail, String subject, String message) throws MessagingException {


        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        MimeMessage msg = new MimeMessage(session);

        //set message headers
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(fromEmail));

        msg.setReplyTo(InternetAddress.parse(fromEmail, false));

        msg.setSubject(subject, "UTF-8");

        msg.setText(message, "UTF-8");

        msg.setSentDate(new Date());

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        Log.i(MailClient.class.getName(),"Message is ready");

        Transport.send(msg);

        Log.i(MailClient.class.getName(),"Email sent to "+toEmail+" Successfully!!");




    }

}


