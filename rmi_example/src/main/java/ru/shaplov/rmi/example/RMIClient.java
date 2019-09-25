package ru.shaplov.rmi.example;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author shaplov
 * @since 25.09.2019
 */
public class RMIClient {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        PaymentService stub = (PaymentService) registry.lookup("PaymentService");
        Payment payment = new Payment();
        payment.setValue(BigDecimal.valueOf(1000.0));
        payment = stub.create(payment);
        System.out.println("Created value with 1000.0");
        stub.findAll().forEach(e -> System.out.println(e.getValue()));
        stub.changeValue(BigDecimal.valueOf(-500.0), payment.getId());
        System.out.println("Updated value to 500.0");
        stub.findAll().forEach(e -> System.out.println(e.getValue()));
        stub.delete(payment.getId());
        System.out.println("Payment with id = " + payment.getId() + " deleted.");
    }
}
