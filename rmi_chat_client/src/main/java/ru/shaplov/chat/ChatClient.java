package ru.shaplov.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Client chat interface.
 *
 * @author shaplov
 * @since 26.09.2019
 */
public interface ChatClient extends Remote {

    /**
     * Post msg to server.
     * @param msg text.
     * @throws RemoteException possible exception.
     */
    void postMsg(String msg) throws RemoteException;

    /**
     * Print msg at client side.
     * @param msg text.
     * @throws RemoteException possible exception.
     */
    void printMsg(String msg) throws RemoteException;

    /**
     * @return Client name.
     * @throws RemoteException possible exception.
     */
    String getName() throws RemoteException;
}
