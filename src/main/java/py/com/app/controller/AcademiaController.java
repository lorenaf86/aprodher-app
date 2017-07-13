package py.com.app.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import py.com.app.data.AcademiaFacade;
import py.com.app.model.Academia;
import py.com.app.util.AbstractController;
import py.com.app.util.AppHelper;
import py.com.app.util.CalendarHelper;
import py.com.app.util.Credentials;
import py.com.app.util.NavigationRulezHelper;


@ViewScoped
@ManagedBean
public class AcademiaController extends AbstractController<Academia> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8696302063086992902L;

	@EJB
    private AcademiaFacade service;
	    
    private List<Academia>  list;
    
    @Inject
    Credentials credentials;

    public AcademiaController() {
        super(Academia.class);
    }

    @PostConstruct
    public void init() {
        super.setService(service);
    }
    
    public void confirm(String accion){
        if(accion.equals("new")){
        	this.getSelected().setEstado("AC");
                this.getSelected().setUsuAlta(this.getCredentials().getUsername());
                this.getSelected().setFechaAlta(CalendarHelper.getCurrentTimestamp());
                this.getSelected().setUsuMod(this.getCredentials().getUsername());
                this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
                this.saveNew(null);
        }else{
                if(accion.equals("edit")){

                        this.getSelected().setUsuMod(this.getCredentials().getUsername());
                        this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
                        this.save(null);
                }else{
                        this.getSelected().setEstado("IN");
                        this.delete(null);
                }
        }
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/academia/index.xhtml");
    }

    public Credentials getCredentials() {
            return credentials;
    }

    public List<Academia> getList() {
            return list;
    }

    public void setList(List<Academia> list) {
            this.list = list;
    }

    public AcademiaFacade getService() {
            return service;
    }

    public void setService(AcademiaFacade service) {
            this.service = service;
    }

    @Override
    public List<Academia> getItems()
    {
        return super.getItems();
    }

}
