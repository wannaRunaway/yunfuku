package com.aifubook.book.mine.self;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SerializableMap implements Serializable {
    private Map<String, String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
