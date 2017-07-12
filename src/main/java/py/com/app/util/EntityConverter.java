/**
 * Copyright 2014 FPTI-PY  (SGPTI)
           www.pti.org.py)
 * pti
 * SGPTI
 * FPTI-PY
 * 
 * as you wish... at your service
 * 
 * ptiDB
 * 
 *            
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package py.com.app.util;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

/**
 * @author ievans
 * from duke's store example
 * 
 * adapted by @author SGPTI [pti]
 *                    www.pti.org.py
 *                    noc@pti.org.py
 */
public class EntityConverter
{

    private static final String key = "org.pti.converter.EntityConverter";

    public EntityConverter() { }

    protected Map<String, Object> getViewMap(FacesContext context)
    {
        Map<String, Object> viewMap = context.getViewRoot().getViewMap();
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Map<String, Object> idMap = (Map) viewMap.get(key);
        if (idMap == null)
        {
            idMap = new HashMap<String, Object>();
            viewMap.put(key, idMap);
        }
        return idMap;
    }
}
