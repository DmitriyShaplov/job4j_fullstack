package ru.shaplov.shop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shaplov.shop.domain.Notification;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author shaplov
 * @since 17.09.2019
 */
@Service
public class ReadMailImpl implements ReadMail {

    private static final Logger LOG = LogManager.getLogger(ReadMailImpl.class);

    private final String host;
    private final String user;
    private final String password;

    public ReadMailImpl(@Value("${spring.mail.imap.host}") String host,
                        @Value("${spring.mail.username}") String user,
                        @Value("${spring.mail.password}") String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<Notification> receiveMessage() {
        final var msgs = new ArrayList<Notification>();
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.imap.host", host);
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
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
