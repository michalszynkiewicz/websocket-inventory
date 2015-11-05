package com.mszynkiewicz.storage.job;

import com.mszynkiewicz.storage.dao.StorageDao;
import com.mszynkiewicz.storage.dto.InventoryItem;
import com.mszynkiewicz.storage.service.StateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 11/5/15
 * Time: 1:37 PM
 */
@Component
public class StateUpdateJob {

    @Autowired
    private StorageDao storageDao;

    @Autowired
    private StateCache stateCache;

    @Scheduled(cron = "*/1 * * * * ?")
    public void updateCache() {
        List<InventoryItem> newInventory = storageDao.getItems();
        stateCache.putState(newInventory);
    }

    @PostConstruct
    public void init() {
        updateCache();
    }
}
