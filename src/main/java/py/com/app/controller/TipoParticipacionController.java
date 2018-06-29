package py.com.app.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import py.com.app.data.TipoParticipacionFacade;
import py.com.app.model.TipoParticipacion;
import py.com.app.util.AbstractController;
import py.com.app.util.AppHelper;
import py.com.app.util.CalendarHelper;
import py.com.app.util.Credentials;
import py.com.app.util.NavigationRulezHelper;


@ViewScoped
@ManagedBean
public class TipoParticipacionController extends AbstractController<TipoParticipacion> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 776230617975651658L;

	@EJB
    private TipoParticipacionFacade service;
	    
    private List<TipoParticipacion>  list;
    
    @Inject
    Credentials credentials;

    public TipoParticipacionController() {
        super(TipoParticipacion.class);
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
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/tipoParticipacion/index.xhtml");
    }

    public Credentials getCredentials() {
            return credentials;
    }

    public List<TipoParticipacion> getList() {
            return list;
    }

    public void setList(List<TipoParticipacion> list) {
            this.list = list;
    }

    public TipoParticipacionFacade getService() {
            return service;
    }

    public void setService(TipoParticipacionFacade service) {
            this.service = service;
    }

    @Override
    public List<TipoParticipacion> getItems()
    {
        return super.getItems();
    }

}
