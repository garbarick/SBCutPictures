package ru.net.serbis.cut.pictures.bean;

import java.util.*;
import java.io.*;

public class Holder<K, V> implements Serializable
{
    private Map<K, V> data = new TreeMap<K, V>();

    public void put(K key, V value)
    {
        data.put(key, value);
    }

    public V get(K key)
    {
        return data.get(key);
    }

    public K getKey(V value)
    {
        for (Map.Entry<K, V> entry : data.entrySet())
        {
            if (entry.getValue() == value)
            {
                return entry.getKey();
            }
        }
        return null;
    }

    public Set<Map.Entry<K, V>> entrySet()
    {
        return data.entrySet();
    }
    
    public Map<K, V> get()
    {
        return data;
    }
}
