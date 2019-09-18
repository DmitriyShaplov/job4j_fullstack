package ru.shaplov.shop.service;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author shaplov
 * @since 18.09.2019
 */
public class ShopClient {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:7779/ws/shop?wsdl");
        QName qname = new QName("http://service.shop.shaplov.ru/", "ShopServiceImplService");
        Service service = Service.create(url, qname);
        ShopService shop = service.getPort(ShopService.class);
        shop.list().getItem().forEach(item -> System.out.println(item.getName()));
    }
}
