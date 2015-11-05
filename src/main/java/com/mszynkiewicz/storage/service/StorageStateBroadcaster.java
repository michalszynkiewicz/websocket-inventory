package com.mszynkiewicz.storage.service;

import com.mszynkiewicz.storage.dto.InventoryItem;
import com.mszynkiewicz.storage.dto.StateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 11/4/15
 * Time: 10:32 PM
 */
@Service
public class StorageStateBroadcaster implements StateUpdateListener {

    @Autowired
    private SimpMessagingTemplate template;

    public void broadcast(Object message) {
        template.convertAndSend("/topic/storage", message);
    }

    @Override
    public void stateChanged(List<InventoryItem> before, List<InventoryItem> after) {
        List<InventoryItem> diff = generateDiff(before, after);
        broadcast(StateDto.from(diff));
    }

    private List<InventoryItem> generateDiff(List<InventoryItem> before, List<InventoryItem> after) {
        List<InventoryItem> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(before)) {
            return after;
        }
        HashMap<String, Integer> beforeMap = toMap(before);
        for (InventoryItem item : after) {
            Integer oldNumber = beforeMap.get(item.getName());
            if (!oldNumber.equals(item.getNumber())) {
                result.add(item);
            }
        }

        return result;
    }

    private HashMap<String, Integer> toMap(List<InventoryItem> items) {
        HashMap<String, Integer> resultMap = new HashMap<>();
        for (InventoryItem item : items) {
            resultMap.put(item.getName(), item.getNumber());
        }

        return resultMap;
    }
}
