/**
 * 
 */
package py.com.app.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.jboss.logging.Logger;

@Startup
@Singleton
public class GlobalConfigParameters extends ConfigParametersHelper
{
    private static final Logger LOGGER = Logger.getLogger(GlobalConfigParameters.class);

    @PostConstruct
    public void startup()
    {
    }

    @PreDestroy
    private void shutdown()
    {
        LOGGER.info("cleaning configuration parameters");
        super.getCfgInfo().clear();
        LOGGER.info("shutting down aplication...");
    }



    @Override
    protected void logError(Exception e, String msg)
    {
        e.printStackTrace();
        LOGGER.error(e.getMessage());
        MessageUtil.addFacesMessageError(msg);
    }

    
    
    private final static String sessionIdPrefix = "aprodher-session-id[";
    private final static String sessionIdSuffix = "]";

    public String getMainModuleUri()
    {
        return "/aprodher-app";
    }
    
    public String buildSessionId(String ip)
    {
        return new StringBuffer()
                    .append(sessionIdPrefix)
                        .append(ip)
                            .append(sessionIdSuffix)
                    .toString(); 
    }
    
    
}
