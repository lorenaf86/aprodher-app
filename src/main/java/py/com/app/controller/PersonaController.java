package py.com.app.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import py.com.app.data.PersonaFacade;
import py.com.app.model.Persona;
import py.com.app.util.AbstractController;
import py.com.app.util.AppHelper;
import py.com.app.util.CalendarHelper;
import py.com.app.util.Credentials;
import py.com.app.util.NavigationRulezHelper;


@ViewScoped
@ManagedBean
public class PersonaController extends AbstractController<Persona> implements Serializable {

    @EJB
    private PersonaFacade service;
	    
    private List<Persona>  list;
    
    @Inject
    Credentials credentials;

    public PersonaController() {
        super(Persona.class);
    }

    @PostConstruct
    public void init() {
        super.setService(service);
    }
    
    @Override
    public Persona prepareCreate(ActionEvent event)
    {
//        this.getSelected().setPersona(new Persona());
        return super.prepareCreate(event);
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
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/persona/index.xhtml");
    }

    public Credentials getCredentials() {
            return credentials;
    }

    public List<Persona> getList() {
            return list;
    }

    public void setList(List<Persona> list) {
            this.list = list;
    }

    public PersonaFacade getService() {
            return service;
    }

    public void setService(PersonaFacade service) {
            this.service = service;
    }

    @Override
    public List<Persona> getItems()
    {
        return super.getItems();
    }

}
