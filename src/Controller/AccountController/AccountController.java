/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.AccountController;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Lilium Aikia
 */
public class AccountController 
{
    private FacebookController _facebookController;
    public FacebookController getFacebookController()
    {
        return _facebookController;
    }
    
    public AccountController()
    {
        _facebookController = new FacebookController();
    }
    
    
    public void sendInviteMail(String email, String password,String recipient)
    {
        String from = email;
        String pass = password;
        String[] to = { recipient }; // list of recipient email addresses
        String subject = "Mời tham gia Untiword";
        String body = "Bạn đã được mời tham gia tài liệu, click vào link sau đây nếu đồng ý";
        
        sendFromGmail(from, pass, to, subject, body);
    }
    
    private static void sendFromGmail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (InternetAddress toAddres : toAddress) {
                message.addRecipient(Message.RecipientType.TO, toAddres);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
        }
        catch (MessagingException me) {
        }
    }
}
