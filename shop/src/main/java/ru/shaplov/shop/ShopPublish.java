package ru.shaplov.shop;

import ru.shaplov.shop.service.ShopServiceImpl;

import javax.xml.ws.Endpoint;

/**
 * @author shaplov
 * @since 18.09.2019
 */
public class ShopPublish {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:7779/ws/shop", new ShopServiceImpl());
    }
}
