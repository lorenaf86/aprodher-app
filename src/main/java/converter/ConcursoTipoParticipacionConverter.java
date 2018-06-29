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
package converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import py.com.app.model.ConcursoTipoParticipacion;
import py.com.app.util.EntityConverter;
import py.com.app.util.GlobalParameters;

@FacesConverter(forClass=ConcursoTipoParticipacion.class)
public class ConcursoTipoParticipacionConverter extends EntityConverter implements Converter
{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if(value == null || value.isEmpty())
        {
            return null;
        }
        
        return getViewMap(context).get(value+ConcursoTipoParticipacion.class.getName());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object)
    {
        if(object == null) return null;
        
        try
        {
            Integer id = ((ConcursoTipoParticipacion) object).getId();
            if (id != null)
            {
                getViewMap(context).put(id.toString()+ConcursoTipoParticipacion.class.getName(), object);
                
                return id.toString();
            }
        }
        catch (ClassCastException cce)
        {
            FacesMessage errMsg = new FacesMessage(GlobalParameters.CONVERSION_ERROR_MESSAGE_ID);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }        
        
        return null;
        
    }

}
