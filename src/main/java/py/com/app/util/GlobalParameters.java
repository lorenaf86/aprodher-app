/**
 * 
 */
package py.com.app.util;

import java.util.Locale;

public class GlobalParameters
{
    /** custom jboss-as-7.1.1.Final configuration folders */
    public static final String MODULE_SERVER_CONFIG_DIR = "aprodher-app";

    public static final String CONVERSION_ERROR_MESSAGE_ID = "ConversionError";    

    public static final int SEARCH_MAX_RESULT = 20;
    
    public static final String SERVER = "server";
    public static final String PORT = "port";
    public static final String MODULENAME="moduleName";
    public static final String SERVICENAME = "serviceName";
    public static final String LOGINPATH = "loginPath";
    public static final String HASHCHECKPATH = "checkSessionIdPath";
    public static final String ACTIVEUSERS = "activeUsers";
    
    public static final String REST_MAIN_CONFIG_FILE_FOLDER = "WEB-INF/lib";
    
    
    public static final String ERROR_KEY_SEVER = "errormessagesserver";
    public static final String ERROR_KEY_PORT = "errormessagesport";
    public static final String ERROR_KEY_MODULENAME = "errormessagesModuleName";
    public static final String ERROR_KEY_SERVICEPATH = "errormessagesServicePath";
    public static final String ERROR_KEY_SERVICENAME = "errormessagesServiceName";


    public static final int RESPONSE_ERROR_LOGIN_FAILED = 1001;
    public static final int RESPONSE_ERROR_REGISTERED_USER = 1002;
    public static final int RESPONSE_ERROR_ACTIVE_USER = 1003;
    public static final int RESPONSE_ERROR_HASHSESSION_NOTFOUND = 1004;
    
    public static final int RESPONSE_ERROR_CONNECTION = 2001;
    public static final int RESPONSE_ERROR_CONFIGURATION = 2002;
    
    public static final int RESPONSE_ERROR_DB_EXCEPTION = 3001;
    public static final int RESPONSE_ERROR_DB_ENTITY_NOT_FOUND = 3002;
    
    
    public static final String COMBOBOX_DESCRIPTOR = "comboBoxDescriptor";
    public static final String DESCRIPTION_DESCRIPTOR = "description";
    
    public static final Locale CURRENT_LOCALE =  null;
    public static final String REP_CONFIG_FILE_FOLDER = "/home/aprodher-app/";
    
    public static final String BUNDLE_URL = "/Bundle"; //src.main.resources.

    public static final String FACES_REDIRECT = "?faces-redirect=true";
    
    public static final String ROOT = "/index.xhtml";
    
    public static final String HOME = "/home.xhtml" + FACES_REDIRECT;

    public static final String INDEX_JSF = "/index.jsf";


}
