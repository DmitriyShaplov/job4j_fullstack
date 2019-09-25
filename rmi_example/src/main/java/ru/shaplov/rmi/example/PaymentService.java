package ru.shaplov.rmi.example;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author shaplov
 * @since 25.09.2019
 */
public interface PaymentService extends Remote  {
    List<Payment> findAll() throws RemoteException;
    Payment create(Payment payment) throws RemoteException;
    void changeValue(BigDecimal amount, int id) throws RemoteException;
    void delete(int id) throws  RemoteException;
}