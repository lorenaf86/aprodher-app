package py.com.app.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import py.com.app.data.UsuarioFacade;
import py.com.app.model.Usuario;
import py.com.app.util.AbstractController;
import py.com.app.util.AppHelper;
import py.com.app.util.CalendarHelper;
import py.com.app.util.Credentials;
import py.com.app.util.NavigationRulezHelper;


@ViewScoped
@ManagedBean
public class UsuarioController extends AbstractController<Usuario> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6905500494476175004L;

	@EJB
    private UsuarioFacade service;
	    
    private List<Usuario>  list;
    
    @Inject
    Credentials credentials;

    public UsuarioController() {
        super(Usuario.class);
    }

    @PostConstruct
    public void init() {
        super.setService(service);
    }
    
    public void prepareNew() {
        super.prepareCreate();
        
//        this.setWizardTitle(this.selectedSolicitudConcepto.getNombre() + " (Agregar)");
          RequestContext.getCurrentInstance().execute("PF('usuarioCreateDialog').show();");
    }

    public void confirm(String accion){
        if(accion.equals("new")){
        	this.getSelected().setEstado("AC");
                this.getSelected().setUsuAlta(this.getCredentials().getUsername());
                this.getSelected().setFechaAlta(CalendarHelper.getCurrentTimestamp());
                this.getSelected().setUsuMod(this.getCredentials().getUsername());
                this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
                this.getSelected().setActivo(Boolean.FALSE);
                this.getSelected().setBloqueado(Boolean.FALSE);
                this.saveNew(null);
        }else{
                if(accion.equals("edit")){

                        this.getSelected().setUsuMod(this.getCredentials().getUsername());
                        this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
                        this.save(null);
                }else{

                        if(accion.equals("activar")){
                            this.getSelected().setActivo(Boolean.TRUE);
                            this.getSelected().setUsuMod(this.getCredentials().getUsername());
                            this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
                        }else{
                            this.getSelected().setEstado("IN");
                            this.delete(null);
                        }
                }
        }
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/usuario/index.xhtml");
    }

    public Credentials getCredentials() {
            return credentials;
    }

    public List<Usuario> getList() {
            return list;
    }

    public void setList(List<Usuario> list) {
            this.list = list;
    }

    public UsuarioFacade getService() {
            return service;
    }

    public void setService(UsuarioFacade service) {
            this.service = service;
    }

    @Override
    public List<Usuario> getItems()
    {
        return super.getItems();
    }

}
