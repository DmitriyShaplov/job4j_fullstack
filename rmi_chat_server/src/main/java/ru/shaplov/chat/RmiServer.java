package ru.shaplov.chat;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * @author shaplov
 * @since 30.09.2019
 */
@SpringBootApplication
public class RmiServer {

    @Bean
    ChatServer chatServer() {
        return new ChatServerImpl();
    }

    @Bean
    RmiServiceExporter exporter(ChatServer implementation) {
        Class<ChatServer> serviceInterface = ChatServer.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(RmiServer.class, args);
    }
}
