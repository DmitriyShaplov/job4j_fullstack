package ru.shaplov.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.shaplov.domain.Notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author shaplov
 * @since 17.09.2019
 */
@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOG = LogManager.getLogger(MailServiceImpl.class);

    private final Properties properties;

    public MailServiceImpl() throws IOException {
        properties = new Properties();
        properties.load(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("mail.properties")));
    }

    @Override
    public void sendMessage(Notification notification) {
        try {
            Session session = Session.getDefaultInstance(
                    properties,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(properties.getProperty("mail.username"),
                                    properties.getProperty("mail.password"));
                        }
                    }
            );
            Message message = new MimeMessage(session);
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(notification.getTo())
            );
            message.setSubject(notification.getSubject());
            message.setText(notification.getBody());
            Transport.send(message);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Notification> receiveMessage() {
        final var msgs = new ArrayList<Notification>();
        try {
            Session emailSession = Session.getDefaultInstance(
                    properties,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(properties.getProperty("mail.username"),
                                    properties.getProperty("mail.password"));
                        }
                    });
            Store store = emailSession.getStore("imaps");
            String host = properties.getProperty("mail.imap.host");
            String user = properties.getProperty("mail.username");
            String password = properties.getProperty("mail.password");
            store.connect(host, user, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            for (var msg : inbox.getMessages()) {
                String body = "";
                Object content = msg.getContent();
                if (content instanceof String) {
                    body = (String) content;
                } else if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    Object o = multipart.getBodyPart(0).getContent();
                    if (o instanceof String) {
                        body = (String) o;
                    }
                }
                msgs.add(new Notification(null, msg.getFrom()[0].toString(),
                        msg.getSubject(), body));
            }
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return msgs;
    }
}
