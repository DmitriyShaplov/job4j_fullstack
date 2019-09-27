package ru.shaplov.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author shaplov
 * @since 26.09.2019
 */
public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {

    private final ChatServer server;
    private final String name;

    public ChatClientImpl(ChatServer server, String name) throws RemoteException {
        this.name = name;
        this.server = server;
    }

    @Override
    public void postMsg(String msg) throws RemoteException {
        server.publish(name + ": " + msg);
    }

    @Override
    public void printMsg(String msg) throws RemoteException {
        System.out.println(msg);
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }
}
