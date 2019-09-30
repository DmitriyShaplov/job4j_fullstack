package ru.shaplov.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import java.util.Scanner;

/**
 * @author shaplov
 * @since 30.09.2019
 */
@SpringBootApplication
public class RmiClient {

    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/ChatServer");
        rmiProxyFactoryBean.setServiceInterface(ChatServer.class);
        return rmiProxyFactoryBean;
    }

    public static void main(String[] args) throws Exception {
        ChatServer server = SpringApplication.run(RmiClient.class, args)
                .getBean(ChatServer.class);
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
}
