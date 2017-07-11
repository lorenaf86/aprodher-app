package py.com.app.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import py.com.app.data.CategoriaFacade;
import py.com.app.model.Categoria;
import py.com.app.util.AbstractController;
import py.com.app.util.AppHelper;
import py.com.app.util.CalendarHelper;
import py.com.app.util.Credentials;
import py.com.app.util.NavigationRulezHelper;


@ViewScoped
@ManagedBean
public class CategoriaController extends AbstractController<Categoria> implements Serializable {

    @EJB
    private CategoriaFacade service;
	    
    private List<Categoria>  list;
    
    @Inject
    Credentials credentials;

    public CategoriaController() {
        super(Categoria.class);
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
        
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/categoria/index.xhtml");
    }

    public Credentials getCredentials() {
            return credentials;
    }

    public List<Categoria> getList() {
            return list;
    }

    public void setList(List<Categoria> list) {
            this.list = list;
    }

    public CategoriaFacade getService() {
            return service;
    }

    public void setService(CategoriaFacade service) {
            this.service = service;
    }

    @Override
    public List<Categoria> getItems()
    {
        return super.getItems();
    }

}
