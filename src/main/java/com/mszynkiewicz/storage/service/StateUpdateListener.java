package com.mszynkiewicz.storage.service;

import com.mszynkiewicz.storage.dto.InventoryItem;

import java.util.List;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 11/5/15
 * Time: 9:35 PM
 */
public interface StateUpdateListener {
    void stateChanged(List<InventoryItem> before, List<InventoryItem> after);
}
