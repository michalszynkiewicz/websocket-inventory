package com.mszynkiewicz.storage.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 11/5/15
 * Time: 10:10 AM
 */
public class StateDto {
    private List<InventoryItem> state = new ArrayList<>();

    public List<InventoryItem> getState() {
        return state;
    }

    public StateDto(List<InventoryItem> state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "StateDto{" +
                "state=" + state +
                '}';
    }

    public static StateDto from(List<InventoryItem> items) {
        ArrayList<InventoryItem> state = new ArrayList<>(items);
        return new StateDto(state);
    }
}
