package com.task.utils;

import java.util.Properties;
import java.util.Random;
import java.util.SplittableRandom;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utils {
	
	public static final String DB_DATETIME_FORMAT ="yyyy-MM-dd HH:mm:ss";

	public static String generateOTP(int otpLength) {

		StringBuilder sb = new StringBuilder();
		SplittableRandom  random = new SplittableRandom();

		for (int i = 0; i < otpLength; i++) {

			sb.append(random.nextInt(0,10));

		}

		return sb.toString();

	}


	public static boolean sendOTPEmail(String recipient, String OTP) { 
		boolean success = false;

		// email ID of Recipient. // String recipient = "renipnr@gmail.com";

		// email ID of Sender. 

		String sender = "renipnr@gmail.com";


		String host = "smtp.gmail.com";

		// Getting system properties 
		Properties properties = System.getProperties();

		// Setting up mail server 
		properties.setProperty("mail.smtp.host", host);

		// creating session object to get properties 
		Session session = Session.getDefaultInstance(properties);

		try { // MimeMessage object. 
			MimeMessage message = new MimeMessage(session);

			// Set From Field: adding senders email to from field. 
			message.setFrom(new InternetAddress(sender));

			// Set To Field: adding recipient's email to from field.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			// Set Subject: subject of the email 
			message.setSubject("OTP Verification");

			// set body of the email. 
			message.setText("OTP : "+OTP);

			// Send email. 
			Transport.send(message);
			System.out.println("Mail successfully sent"); success = true ; 
		} catch (MessagingException mex) {
			mex.printStackTrace(); 
		}

		return success ;

	}



	public static boolean sendOTPEmail2(String recipient, String OTP) {
		boolean success = false;

		// email ID of Recipient.
		//	      String recipient = "renipnr@gmail.com";

		// email ID of  Sender.
		String sender = "renipnr@gmail.com";
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		String host = "smtp.gmail.com";

		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		final String username = sender;//
		final String password = "athira!206067";
		// creating session object to get properties
		Session session = Session.getDefaultInstance(props);

		try 
		{
			/*
			 * Session session = Session.getInstance(props, new Authenticator(){ protected
			 * PasswordAuthentication getPasswordAuthentication() { return new
			 * PasswordAuthentication(username, password); }});
			 */
			// MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From Field: adding senders email to from field.
			message.setFrom(new InternetAddress(sender));

			// Set To Field: adding recipient's email to from field.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			// Set Subject: subject of the email
			message.setSubject("OTP Verification");

			// set body of the email.
			message.setText("OTP : "+OTP);

			// Send email.
			Transport.send(message);
			System.out.println("Mail successfully sent");
			success = true ;
		}
		catch (MessagingException mex) 
		{
			mex.printStackTrace();
		}

		return success ;
	}
	
	public static boolean sendOTPEmail3(String recipient, String OTP) {
	 
		boolean success = false;
		String from = "renipnr@gmail.com";
        String pass = "athira206067";
        String[] to = { recipient }; // list of recipient email addresses
        String subject = "OTP Verification";
        String body = "OTP : "+OTP;
        
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

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
            System.out.println("Mail successfully sent");
			success = true ;
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }

		
		return success;
	}

}
