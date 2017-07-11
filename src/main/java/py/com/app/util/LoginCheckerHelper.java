/**
 * 
 */
package py.com.app.util;

import javax.servlet.ServletContext;

public abstract class LoginCheckerHelper
{
    protected String hash = null;
    protected boolean checked = false;
    
    public ServletContext getContext()
    {
        return AppHelper.getServletContext().getContext("/aprodher-app");
    }
    
    protected String getSessionId()
    {
        return (String) getContext().getAttribute(getSessionIdKey());
    }    
    
    public void init() 
    {
        hash = getSessionId();
        checked = false;
    }
    
    public boolean isChecked()
    {
        return checked;
    }
    
    public void invalidate()
    {
        hash = null;
        checked = false;
    }

    protected boolean hashIsDifferent()
    {
        if(hash.equals(getSessionId())) {
            return false;
        }
        
        hash = getSessionId();
        return true;
    }

    private String getSessionIdKey()
    {
        return new StringBuffer()
                        .append("aprodher-session-id[")
                            .append(AppHelper.getClientIpAddr())
                                .append("]")
                        .toString(); 
    }
    
}
