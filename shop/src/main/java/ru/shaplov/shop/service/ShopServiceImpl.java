package ru.shaplov.shop.service;

import ru.shaplov.shop.domain.Item;

import javax.jws.WebService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shaplov
 * @since 18.09.2019
 */
@WebService(endpointInterface = "ru.shaplov.shop.service.ShopService")
public class ShopServiceImpl implements ShopService {

    private final Map<Integer, Item> items = new ConcurrentHashMap<>();

    {
        Item item = new Item();
        item.setId(1);
        item.setName("test");
        items.put(item.getId(), item);
    }

    @Override
    public Item[] list() {
        return items.values().toArray(new Item[0]);
    }

    @Override
    public Item findById(int id) {
        return items.get(id);
    }

    @Override
    public Item crete(Item item) {
        Item v = items.putIfAbsent(item.getId(), item);
        return v == null ? item : v;
    }

    @Override
    public void update(Item item) {
        items.put(item.getId(), item);
    }

    @Override
    public void delete(int id) {
        items.remove(id);
    }
}
