package com.mszynkiewicz.storage.dao;

import com.mszynkiewicz.storage.dto.InventoryItem;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 11/5/15
 * Time: 1:39 PM
 */
@Component
public class MockStorageDao implements StorageDao {

    private final Map<String, Integer> storage = new HashMap<>();

    public MockStorageDao() {
        storage.put("bricks", 112);
        storage.put("hammers", 122);
        storage.put("drillers", 132);
        storage.put("brushes", 142);
        storage.put("screwdrivers", 142);
    }

    @Override
    public synchronized List<InventoryItem> getItems() {
        randomizeAmounts();
        return getStorageAsInventoryItems();
    }

    private List<InventoryItem> getStorageAsInventoryItems() {
        return storage.entrySet()
                .stream()
                .map(e -> new InventoryItem(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private void randomizeAmounts() {
        // in order to observe the incremental (by diff) update via websocket, we modify only two items
        Random random = new Random();
        for (String key : getTwoRandomKeys()) {
            storage.put(key, random.nextInt(10000));
        }
    }

    private List<String> getTwoRandomKeys() {
        ArrayList<String> keyList = new ArrayList<>(storage.keySet());
        Collections.shuffle(keyList);
        return keyList.subList(0, 2);
    }
}
