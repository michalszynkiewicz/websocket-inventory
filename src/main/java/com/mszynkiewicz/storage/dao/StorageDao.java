package com.mszynkiewicz.storage.dao;


import com.mszynkiewicz.storage.dto.InventoryItem;

import java.util.List;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 11/5/15
 * Time: 1:37 PM
 */
public interface StorageDao {
    List<InventoryItem> getItems();
}
