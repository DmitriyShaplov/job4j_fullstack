package ru.shaplov.rmi.example;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {
    public static void main(String[] args) throws Exception {
        PaymentServiceDB obj = new PaymentServiceDB();
        PaymentService stub = (PaymentService) UnicastRemoteObject.exportObject(obj, 5000);
        LocateRegistry.createRegistry(1099);
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("PaymentService", stub);
    }
}
