package ru.shaplov.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Server side chat interface.
 *
 * @author shaplov
 * @since 26.09.2019
 */
public interface ChatServer extends Remote {
    /**
     * Register new client in chat.
     * @param chatClient Client obj.
     * @throws RemoteException possible exception.
     */
    void register(ChatClient chatClient) throws RemoteException;

    /**
     * publish msg to all clients.
     * @param msg message.
     * @throws RemoteException possible exception.
     */
    void publish(String msg) throws RemoteException;
}
