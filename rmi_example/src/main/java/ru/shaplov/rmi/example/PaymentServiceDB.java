package ru.shaplov.rmi.example;

import ru.shaplov.rmi.example.persistence.DAOPayment;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author shaplov
 * @since 26.09.2019
 */
public class PaymentServiceDB implements PaymentService {

    private DAOPayment dao = DAOPayment.getInstance();

    @Override
    public List<Payment> findAll() throws RemoteException {
        return dao.list();
    }

    @Override
    public Payment create(Payment payment) throws RemoteException {
        return dao.create(payment);
    }

    @Override
    public void changeValue(BigDecimal amount, int id) {
        dao.changeValue(amount, id);
    }

    @Override
    public void delete(int id) {
        Payment payment = new Payment();
        payment.setId(id);
        dao.delete(payment);
    }
}
