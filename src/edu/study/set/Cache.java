package edu.study.set;

import java.util.ArrayList;
import java.util.Map;

public interface Cache<T> {
    void save(String key, T value);
    T get(String key);
}

class MemoryCache<T> implements Cache<T> {
    Map map;

    @Override
    public void save(String key, T value) {
        map.put(key, value);
    }

    @Override
    public T get(String key) {
        return (T) map.get(key);
    }
}

class Test{
    public static void main(String[] args) {
        MemoryCache<String> mcs = new MemoryCache<String>();
        mcs.save("key1", "value1");
        mcs.save("key2", "value2");
        System.out.println(mcs.get("key1"));
        System.out.println(mcs.get("key2"));
    }
}