package ru.shaplov.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Server side chat interface.
 *
 * @author shaplov
 * @since 26.09.2019
 */
public interface ChatServer {
    /**
     * Register new client in chat.
     * @param chatClient Client obj.
     */
    void register(ChatClient chatClient);

    /**
     * publish msg to all clients.
     * @param msg message.
     */
    void publish(String msg);
}
