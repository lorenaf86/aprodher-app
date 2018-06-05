package py.com.app.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import py.com.app.data.ConcursoAcademiaCoreoFacade;
import py.com.app.data.ModalidadFacade;
import py.com.app.model.Modalidad;
import py.com.app.util.AbstractController;
import py.com.app.util.AppHelper;
import py.com.app.util.CalendarHelper;
import py.com.app.util.Credentials;
import py.com.app.util.NavigationRulezHelper;


@ViewScoped
@ManagedBean
public class ModalidadController extends AbstractController<Modalidad> implements Serializable {

    @EJB
    private ModalidadFacade service;
	    
    @EJB
    private ConcursoAcademiaCoreoFacade serviceConcurso;

    private List<Modalidad>  list;
    
    @Inject
    Credentials credentials;

    public ModalidadController() {
        super(Modalidad.class);
    }

    @PostConstruct
    public void init() {
        super.setService(service);
    }
    
    public void confirm(String accion){
        if(accion.equals("new")){
        	this.getSelected().setEstado("AC");
        	this.getSelected().setConcurso(serviceConcurso.findConcursoVigente());
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
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/modalidad/index.xhtml");
    }

    public Credentials getCredentials() {
            return credentials;
    }

    public List<Modalidad> getList() {
            return list;
    }

    public void setList(List<Modalidad> list) {
            this.list = list;
    }

    public ModalidadFacade getService() {
            return service;
    }

    public void setService(ModalidadFacade service) {
            this.service = service;
    }

    @Override
    public List<Modalidad> getItems()
    {
        return super.getItems();
    }

}
