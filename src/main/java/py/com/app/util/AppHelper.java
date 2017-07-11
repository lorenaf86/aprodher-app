
package py.com.app.util;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AppHelper
{
    
    public static String getDomainUrl()
    {
    	String host = getExternalContext().getRequestHeaderMap().get("host");
    	String appName = getExternalContext().getRequestContextPath();

    	return "http://" + host + appName;
    }

    public static ExternalContext getExternalContext()
    {
        return FacesContext.getCurrentInstance().getExternalContext();
    }
    
    public static ServletContext getServletContext()
    {
        return (ServletContext) getExternalContext().getContext();
    }
    
    public static HttpServletRequest getServletRequest()
    {
        return (HttpServletRequest) getExternalContext().getRequest();
    }
    
    public static HttpServletResponse getServletResponse()
    {
        return (HttpServletResponse) getExternalContext().getResponse();
    }
    
    public static Map<String, Object> getRequestContext()
    {
        return getExternalContext().getRequestMap();
    }
    
    public static Locale getLocale()
    {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    public static String getBundleMessage(String key)
    {
        return getBundleMessage(GlobalParameters.BUNDLE_URL, key, Locale.getDefault());
    }
    
    /**
     * FIXME add jar to facilitate exceptions' debug and return detailed errors
     * 
     * @param e
     */
    public static void printException(Exception e)
    {
        String message = e.getMessage();
        
        if(e instanceof RollbackException)
        {
            RollbackException rbe = (RollbackException) e;
            Throwable cause = rbe.getCause();

            if(cause instanceof PersistenceException)
            {
                PersistenceException pe = (PersistenceException) cause;
                if(pe.getCause().toString().contains("ConstraintViolationException"))
                {
//                    ConstraintViolationException cve = (ConstraintViolationException) pe.getCause();
                    message = pe.getCause().getLocalizedMessage();
//                    for(ConstraintViolation<?> ex : cve.getConstraintViolations())
//                    {
//                        ex.getConstraintDescriptor();
//                        ex.getInvalidValue();
//                        ex.getRootBean();
//                    }
//                    message = cve.getMessage();
                }
            }
            
            
//            for (InvalidValue invalidValue : ise.getInvalidValues()) 
//            {
//                String msg = invalidValue.getBeanClass().getSimpleName() +
//                             " has an invalid property: " + invalidValue.getPropertyName() +
//                             " with message: " + invalidValue.getMessage() + ".";
//                System.err.println(msg);
//            }
            
        }
        else
        {
            e.printStackTrace();
        }
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "system.errors"));
    }

    public static String getClientIpAddr()
    {
        return getServletRequest().getRemoteAddr();
    }

    public static String getSessionId()
    {
        HttpSession session = (HttpSession) getExternalContext().getSession(false);
        return session.getId();
    }

    public static String createNewSession()
    {
        HttpSession session = (HttpSession) getExternalContext().getSession(true);
        return session.getId();
    }

    public static String fixDTOname(String name)
    {
        if(name.toUpperCase().endsWith("DTO")) 
        {
            name = name.substring(0, name.length()-3);
        }
        return name;
    }

    public static String getBundleMessage(String bundleName, String key, Locale locale)
    {
        try
        {
            ResourceBundle bundle = null;
            if(locale == null) {
                bundle = ResourceBundle.getBundle(bundleName);
            } else {
                bundle = ResourceBundle.getBundle(bundleName, locale);
            }
            
            if(bundle.containsKey(key))
            {
                return bundle.getString(key);
                
            }
            /* 
             * may be is a "common" pre-defined property bundle 
             * look up for it in base common messages
             */
            if(!bundleName.contains("CommonMessages")) {
                return getBundleMessage(key, locale);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Resource Messages NOT found, call admin";
        }
        return null;
    }
    
    public static String getBundleMessage(String key, Locale locale)
    {
        return getBundleMessage(GlobalParameters.BUNDLE_URL, key, locale);
    }

    public static void redirect(String url)
    {
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

//    public static String getRESTfullConfig(String cfgFileName) throws FileNotFoundException, IOException
//    {
//        return UriBuilder.buildLoginUri(FileHelper.loadConfigParams(cfgFileName, SessionParameters.JBOSS7_JBOSSSERVER_EXAMPLE_SERVER_CONN_CONFIG_DIR));
//    }
    
}
