package ru.net.serbis.cut.pictures.util;

import java.lang.reflect.*;
import java.util.*;

public class Reflection
{
    private static final Reflection instance = new Reflection();

    public static Reflection get()
    {
        return instance;
    }
    
    public <T> Map<String, T> getValues(Class holder, Class<T> type)
    {
        Map<String, T> result = new TreeMap<String, T>();
        for (Field field : holder.getFields())
        {
            if (type.isAssignableFrom(field.getType()))
            {
                T param = getValue(field);
                result.put(field.getName(), param);
            }
        }
        return result;
    }

    public <T> Map<String, T[]> getArrayValues(Class holder, Class<T> type)
    {
        Map<String, T[]> result = new TreeMap<String, T[]>();
        for (Field field : holder.getFields())
        {
            if (field.getType().isArray() &&
                type.isAssignableFrom(field.getType().getComponentType()))
            {
                T[] param = getValue(field);
                result.put(field.getName(), param);
            }
        }
        return result;
    }

    public <T> T getValue(Field field)
    {
        try
        {
            return (T) field.get(null);
        }
        catch (Exception e)
        {
            Log.error(this, e);
            return null;
        }
    }
}
