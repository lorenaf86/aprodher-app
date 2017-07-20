/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.util;

import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.EmailException;

/**
 *
 * @author Lorena Franco
 */
public class EmailUtils {
	 private static final String HOSTNAME = "smtp.gmail.com";
	 private static final String USERNAME = "lorenaf86";
	 private static final String PASSWORD = "826060882";
	 private static final String EMAILORIGEM = "lorenaf86@gmail.com";

	 /*private static final String HOSTNAME = "smtp.live.com";
     private static final String USERNAME = "AprodHer";
     private static final String PASSWORD = "aproasoher8";
     private static final String EMAILORIGEM = "aprod_her@hotmail.com";*/
	 

     public static void enviaEmail(Mensaje mensagem) throws EmailException {
    	 
    	Properties props = new Properties();
 		props.put("mail.smtp.auth", "true");
 		props.put("mail.smtp.starttls.enable", "true");
 		props.put("mail.smtp.host", HOSTNAME);
 		props.put("mail.smtp.port", "587");

 		Session session = Session.getInstance(props,
    			  new javax.mail.Authenticator() {
    				protected PasswordAuthentication getPasswordAuthentication() {
    					return new PasswordAuthentication(EMAILORIGEM, PASSWORD);
    				}
    			  });
    	 
		try {
        Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-"+EMAILORIGEM));
		message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse("to-"+mensagem.getDestino()));
		message.setSubject(mensagem.getTitulo());
		message.setText(mensagem.getMensagem());
		
		Transport transport = session.getTransport("smtp");
        transport.connect(HOSTNAME, USERNAME, PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        

		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }    
}
