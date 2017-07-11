
package py.com.app.util;

	import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

public class MessageUtil
{
    
    private static void addMessage(Severity severity, String summary, String detail)
    {
        FacesContext.getCurrentInstance()
                        .addMessage(null
                                  , new FacesMessage(severity, summary, detail)
                        );
    }
    
    public static String retrieveMessage(String message)
    {
        String detail =  AppHelper.getBundleMessage(message);
        if(detail == null) {
            detail = message;
        }
        return detail;
    }

    /**
     * <p>add a message to {@link FacesContext}'s message with the given message</p>
     * <p>if the message represents a key stored in bundle property, will show the corresponding
     *    value store in the file. 
     * </p>
     * @param message
     * 
     */
    public static void addFacesMessage(String message)
    {
        String detail = retrieveMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(detail));
    }

    // INFO MESSAGES
    public static void addFacesMessageInfo(String message)
    {
        addFacesMessageInfo(message, null);
    }
    
    public static void addFacesMessageInfo(String message, String summary)
    {
        String detail = retrieveMessage(message);
        addMessage(FacesMessage.SEVERITY_INFO, summary, detail);
    }

    // WARNM MESSAGES
    public static void addFacesMessageWarm(String message)
    {
        addFacesMessageWarm(message, null);
    }
    
    public static void addFacesMessageWarm(String message, String summary)
    {
        String detail = retrieveMessage(message);
        addMessage(FacesMessage.SEVERITY_WARN, summary, detail);
    }

    // ERROR MESSAGES
    public static void addFacesMessageError(String message)
    {
        addFacesMessageError(message, null);
    }
    
    public static void addFacesMessageError(String message, String summary)
    {
        String detail = retrieveMessage(message);
        addMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
    }

    
    // UTILS
    public static void showResults(List<?> collection)
    {
        showResults("", collection);
    }
    
    
    public static void showResults(String sumary, List<?> collection)
    {
        if(collection == null || collection.isEmpty()) {
            addFacesMessageWarm("search.label.noRecordsFound", sumary);
        } else {
            String message = AppHelper.getBundleMessage("search.label.recordsFound");
            addFacesMessageInfo(message + ": " + collection.size(), sumary);
        }
    }
    public static void showMessageInDialogInfo(String summary, String detail) 
    {
    	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
    
    public static void showMessageInDialogError(String summary, String detail) 
    {
    	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
}
