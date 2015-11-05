package com.mszynkiewicz.storage.dto;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 11/5/15
 * Time: 11:55 AM
 */
public class InventoryItem {
    private String name;
    private Integer number;

    public InventoryItem(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }
}
