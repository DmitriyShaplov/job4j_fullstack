package ru.shaplov.shop.service;

import ru.shaplov.shop.domain.Item;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * @author shaplov
 * @since 18.09.2019
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface ShopService {
    @WebMethod
    Item[] list();

    @WebMethod
    Item findById(int id);

    @WebMethod
    Item crete(Item item);

    @WebMethod
    void update(Item item);

    @WebMethod
    void delete(int id);
}
