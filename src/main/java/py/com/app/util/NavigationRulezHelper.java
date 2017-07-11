package py.com.app.util;

import java.io.IOException;

import javax.faces.context.FacesContext;

public class NavigationRulezHelper
{

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


}
