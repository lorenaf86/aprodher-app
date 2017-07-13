/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.util;

import java.util.ResourceBundle;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

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

     public static Email conectaEmail() throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName(HOSTNAME);
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
        email.setSSL(true);
	email.setTLS(true);
        email.setFrom(EMAILORIGEM);
        return email;
     }

     public static void enviaEmail(Mensaje mensagem) throws EmailException {
        Email email = new SimpleEmail();
        email = conectaEmail();
        email.setSubject(mensagem.getTitulo());
        email.setMsg(mensagem.getMensagem());
        email.addTo(mensagem.getDestino());
        String resposta = email.send();
        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("emailenviado") + resposta);
     }    
}
