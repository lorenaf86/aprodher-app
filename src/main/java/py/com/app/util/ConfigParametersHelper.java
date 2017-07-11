/**
 * 
 */
package py.com.app.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class ConfigParametersHelper
{
    private final Map<String, String> cfgInfo = new HashMap<String, String>();

    protected abstract void logError(Exception e, String msg);
    
    protected void addConnConfigParamater(AprodherProperties parameters)
    {
        Iterator<Object> iterator = parameters.keySet().iterator();
        while (iterator.hasNext())
        {
            String key = iterator.next().toString();
            addConnConfigParamater(key, parameters.get(key).toString());
        }
    }

    public void addConnConfigParamater(String key, String value)
    {
        cfgInfo.put(key, value);
    }
    
    public void addConnConfigParamater(Map<String, String> parameters)
    {
        cfgInfo.putAll(parameters);
    }

    public String getConfigParam(String key)
    {
        if (cfgInfo.containsKey(key)) 
        {
            return cfgInfo.get(key).toString(); 
        }
        return null;
    }

    public Map<String, String> getCfgInfo()
    {
        return cfgInfo;
    }
    

}
