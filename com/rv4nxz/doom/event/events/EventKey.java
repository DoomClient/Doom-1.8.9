package com.rv4nxz.doom.event.events;

import com.rv4nxz.doom.event.Event;

public class EventKey extends Event {
    private int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
