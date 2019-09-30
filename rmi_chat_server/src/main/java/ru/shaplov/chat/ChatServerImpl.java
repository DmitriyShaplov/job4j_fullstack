package ru.shaplov.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author shaplov
 * @since 26.09.2019
 */
public class ChatServerImpl implements ChatServer {

    private static final Logger LOG = LogManager.getLogger(ChatServerImpl.class);

    private List<ChatClient> clients = new LinkedList<>();

    @Override
    public synchronized void register(ChatClient chatClient) {
        try {
            clients.add(chatClient);
            String msg = "User - " + chatClient.getName() + " connected.";
            publish(msg);
            chatClient.printMsg("You are connected.");
        } catch (Exception e) {
            LOG.error("Client registration failed.", e);
        }
    }

    @Override
    public synchronized void publish(String msg) {
        LOG.info(msg);
        ListIterator<ChatClient> listIterator = clients.listIterator();
        while (listIterator.hasNext()) {
            ChatClient client = listIterator.next();
            try {
                client.printMsg(msg);
            } catch (Exception e) {
                listIterator.remove();
            }
        }
    }
}
