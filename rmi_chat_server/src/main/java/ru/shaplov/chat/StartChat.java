package ru.shaplov.chat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author shaplov
 * @since 26.09.2019
 */
public class StartChat {
    public static void main(String[] args) throws Exception {
        ChatServerImpl server = new ChatServerImpl();
        ChatServer stub = (ChatServer) UnicastRemoteObject.exportObject(server, 5000);
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("ChatServer", stub);
    }
}
