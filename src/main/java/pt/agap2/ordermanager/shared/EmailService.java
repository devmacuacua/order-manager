package pt.agap2.ordermanager.shared;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.Logger;

public class EmailService implements IEmailService {

	private static final Logger logger = Log.getLogger(EmailService.class);

	private final Properties config = new Properties();

	public EmailService() {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("mail.properties")) {
			if (input == null) {
				throw new RuntimeException("mail.properties not found in classpath");
			}
			config.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Could not load mail.properties", e);
		}
	}

	public void sendOrderCompletedEmail(String to, Long orderId) {
		boolean enabled = Boolean.parseBoolean(config.getProperty("mail.enabled", "false"));

		if (!enabled) {
			logger.info("EMAIL_SENT_MOCK to={} orderId={}", to, orderId);
			return;
		}

		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", config.getProperty("mail.smtp.auth", "true"));
			props.put("mail.smtp.starttls.enable", config.getProperty("mail.smtp.starttls.enable", "true"));
			props.put("mail.smtp.host", config.getProperty("mail.smtp.host"));
			props.put("mail.smtp.port", config.getProperty("mail.smtp.port"));

			final String username = config.getProperty("mail.username");
			final String password = config.getProperty("mail.password");
			final String from = config.getProperty("mail.from");

			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Order Completed #" + orderId);
			message.setText("Hello,\n\nYour order #" + orderId + " has been completed successfully.\n\nRegards,\nOrder Manager");

			Transport.send(message);

			logger.info("EMAIL_SENT to={} orderId={}", to, orderId);

		} catch (Exception e) {
			logger.error("EMAIL_ERROR to={} orderId={}", to, orderId, e);
			throw new RuntimeException("Could not send email", e);
		}
	}
}