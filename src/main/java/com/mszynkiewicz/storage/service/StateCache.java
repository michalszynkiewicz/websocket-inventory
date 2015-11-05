package com.mszynkiewicz.storage.service;

import com.mszynkiewicz.storage.dto.InventoryItem;
import com.mszynkiewicz.storage.dto.StateDto;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 11/5/15
 * Time: 10:46 AM
 */
@Service
public class StateCache implements ApplicationContextAware {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private Collection<StateUpdateListener> listeners;

    private List<InventoryItem> cache = new ArrayList<>();

    public void putState(List<InventoryItem> items) {
        lock.writeLock().lock();
        List<InventoryItem> oldState = cache;
        try {
            try {
                cache = items;
                lock.readLock().lock();
            } finally {
                lock.writeLock().unlock();
            }
            invokeListeners(oldState, cache);
        } finally {
            lock.readLock().unlock();
        }
    }

    public StateDto getState() {
        lock.readLock().lock();
        try {
            return StateDto.from(cache);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        listeners = applicationContext.getBeansOfType(StateUpdateListener.class).values();
    }

    private void invokeListeners(List<InventoryItem> oldItems, List<InventoryItem> newItems) {
        for (StateUpdateListener listener : listeners) {
            listener.stateChanged(oldItems, newItems);
        }

    }
}
