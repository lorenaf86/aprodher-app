package py.com.app.util;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author JorgePC
 *
 */
public class NavigatorHelper {
	
	public static void redirectIndex() {
        try {
        	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        	String host = externalContext.getRequestHeaderMap().get("host");
        	String appName = externalContext.getRequestContextPath();
        	String url = "http://" + host + appName + "/index.xhtml";
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
