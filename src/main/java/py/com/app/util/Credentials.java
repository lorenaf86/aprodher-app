package py.com.app.util;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Credentials extends BaseCredentials implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -4322372793796346475L;
    private Integer IdAcademia;

    @PostConstruct
    public void init()
    {
        super.init();
    }

}