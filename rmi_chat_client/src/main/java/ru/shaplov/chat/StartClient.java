package ru.shaplov.chat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * @author shaplov
 * @since 26.09.2019
 */
public class StartClient {

    private void start() throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        ChatServer server = (ChatServer) registry.lookup("ChatServer");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a name: ");
        String name = scanner.next();
        scanner.nextLine();
        ChatClient client = new ChatClientImpl(server, name);
        server.register(client);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String msg = scanner.nextLine();
                    if ("exit".equals(msg)) {
                        Thread.currentThread().interrupt();
                    } else {
                        client.postMsg(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public static void main(String[] args) throws Exception {
        new StartClient().start();
    }
}
