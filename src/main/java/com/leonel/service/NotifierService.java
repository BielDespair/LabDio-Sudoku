package com.leonel.service;

import java.util.ArrayList;
import java.util.List;

public class NotifierService {
    private final List<EventListener> listeners = new ArrayList<>();

    public void subscribe(EventListener listener) {
        listeners.add(listener);
    }

    public void notifyAll(EventEnum event) {
        for (EventListener listener : listeners) {
            listener.update(event);
        }
    }
}
