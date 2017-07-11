package py.com.app.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class AprodherProperties extends Properties
{
    /**
     * 
     */
    private static final long serialVersionUID = 3893779781277182768L;
    
    public final static String NULL = "NULL";

    public AprodherProperties()
    {
        super();
    }

    public AprodherProperties(Properties prop)
    {
        if (prop != null)
        {
            putAll(prop);
        }
    }

    public AprodherProperties(Map<?, ?> map)
    {
        if (map != null)
        {
            addProperties(map);
        }
    }

    public AprodherProperties(Object k, Object v)
    {
        super();
        put(k, v == null ? NULL : v);
    }

    public AprodherProperties(Set<String> keynames)
    {
        super();
        Iterator<String> keynameiterator = keynames.iterator();
        while (keynameiterator.hasNext())
        {
            put(keynameiterator.next(), NULL);
        }
    }

    private void addProperties(Map<?, ?> map)
    {
        Iterator<?> iterator = map.keySet().iterator();
        while (iterator.hasNext())
        {
            Object key = iterator.next();
            Object value = map.get(key);

            super.put(key, value == null ? NULL : value);
        }

    }

    
    
    @Override
    public synchronized Object put(Object key, Object value)
    {
        if (value == null)
        {
            value = NULL;
        }
        return super.put(key, value);
    }

    /**
     * simple code to make from NULL again null;
     */
    @Override
    public synchronized Object get(Object key)
    {
        Object returnvalue = super.get(key);
        if ((returnvalue == null) || ((returnvalue instanceof String) && (returnvalue.equals(NULL))))
        {
            return null;
        }
        return returnvalue;
    }

    /**
     * Filters the properties and passes only the serializable properties in a
     * new Properties instance
     * 
     * @param properties
     * @return
     */
    public static Properties FilterSynchronizableProperties(Properties properties)
    {
        AprodherProperties returnmap = new AprodherProperties();
        Iterator<Object> keynames = properties.keySet().iterator();
        while (keynames.hasNext())
        {
            Object key = keynames.next();
            Object value = properties.get(key);
            if ((value == null) || (value instanceof Serializable))
            {
                returnmap.put(key, value);
            }
        }
        return returnmap;
    }

    /**
     * Intended to use in the lookups when in some parts you will only add a
     * criteria if a parameter is there and have a not null value (or empty
     * value in the String case)
     * 
     * @param key
     * @return true if Properties contains the given key and the value is !=
     *         null. If value is a String, it will return true if
     *         value.trim().length > 0 If value is a Collection, it will return
     *         true !value.isEmpty
     */
    public boolean isPropertyFilled(String key)
    {
        if (this.containsKey(key))
        {
            Object value = this.get(key);
            if (value != null)
            {
                if ((value instanceof String) && ((String) value).trim().length() < 1)
                {
                    return false;
                }
                if ((value instanceof Collection) && ((Collection<?>) value).isEmpty())
                {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}