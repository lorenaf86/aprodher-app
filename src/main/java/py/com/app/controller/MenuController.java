/**
 * 
 */
package py.com.app.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import py.com.app.model.Funcionalidad;
import py.com.app.util.AppHelper;
import py.com.app.util.Credentials;
import py.com.app.util.GlobalConfigParameters;
import py.com.app.util.NavigationRulezHelper;

@Model
public class MenuController implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6492062858916303466L;

	@Inject
    Credentials credentials;
    
    @Produces
    @Named
    private String selectedMenuURL = null;
    
    @Inject
    GlobalConfigParameters parameters;
    
    @Inject
    LoginController login;
    
    @Inject
    LoginController loginCheckController;
    
    private boolean exapand = false;

    private MenuModel model;
    private List<Funcionalidad> sistemaList = null;
  //  private List<EmpleadoMovimientoDTO> empleadoMovimientos = null;
    
    public boolean isMenuSelected()
    {
        return selectedMenuURL != null && !selectedMenuURL.isEmpty();
    }
    
    public boolean isExapand()
    {
        return exapand;
    }
    
    @PostConstruct
    public void init(){
        selectedMenuURL = null;
        if (credentials.getUsername() != null && !credentials.getUsername().equals(""))
        	armarMenu();
    }
    
    public void iniciar() {
    	if (!login.isLoggedIn())
    		NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/login.xhtml");
    }
    
    public void redirect(String url)
    {
        exapand = false;
        
        if ("home".equalsIgnoreCase(url))
        {
    		NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/index.xhtml");
        }
        
        
        if(!url.startsWith("http://")) {
            url = "http://" + url;
        }
        
        selectedMenuURL = url;
        
        if(!url.contains("www.")) {
            selectedMenuURL += "?hash=" + credentials.getSessionHash();
        }
        
        String sessionkey = parameters.buildSessionId(credentials.getIpAddr());
        
        AppHelper.getServletContext().setAttribute(sessionkey,credentials.getSessionHash());
        
      //  RequestContext.getCurrentInstance().update("mainpanel");
    }    

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
		
    public void armarMenu() {
    	model = new DefaultMenuModel();
    	DefaultMenuItem item = new DefaultMenuItem("Home");
    	
    	item.setIcon("/resources/gfx/dock/home.png");
    	item.setOutcome("/index");
    	model.addElement(item);    	
    	
    	try {
    		/*sistemaList = sistemaService.findByUsuario(credentials.getUsername());
    		for (Funcionalidad: sistemaList) {
    			item = new DefaultMenuItem(sistema.getNombre());
    			String sigla = sistema.getSigla();
    			String[] apl = sistema.getNombre().split("-");
    			
    			String nomAplicacion = apl[0] + "/index.jsf"; //+ sistema.getNomAplicacion();
    			if (apl[0].equals("app")) nomAplicacion ="administration/index.jsf"; //+ sistema.getNomAplicacion();
    			
    			String uri = navigationRulez.buildUri("/project-client-web/" + nomAplicacion);
    			String command = "#{menuController.redirect('"+ uri +"')}";
    			
    			item.setIcon("/resources/gfx/dock/"+sigla+".png");
    			item.setCommand(command);
    			//item.setUrl(nomAplicacion + "?hash=" + credentials.getSessionHash());
    			model.addElement(item);
    		}*/
//    		String uri = navigationRulez.buildUri("/project-client-web/administration/solicitud/index.jsf");
//    		item = new DefaultMenuItem("Mis Solicitudes");
//        	item.setIcon("/resources/gfx/dock/MS.png");
//        	item.setCommand("#{menuController.redirect('"+ uri +"')}");
//        	model.addElement(item);  
        	
    		item = new DefaultMenuItem("Administrativo");
        	item.setIcon("/resources/gfx/dock/AD.png");
                item.setOutcome("/adm/index");
        	//item.setCommand("#{menuController.validarMovimientosEmpleado()}");
        	model.addElement(item);  
    		
    		item = new DefaultMenuItem("Académico");
        	item.setIcon("/resources/gfx/dock/SM.png");
                item.setOutcome("/publico/index");
        	//item.setCommand("#{menuController.validarMovimientosEmpleado()}");
        	model.addElement(item);  

                } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }

	public List<Funcionalidad> getSistemaList() {
		return sistemaList;
	}

	public void setSistemaList(List<Funcionalidad> sistemaList) {
		this.sistemaList = sistemaList;
	}
	
	public void validarMovimientosEmpleado(){
		
		try {
			/*empleadoMovimientos = empleadoMovimientoClientService.findByEmpleadoId(credentials.getIdEmpleado());
			if (empleadoMovimientos.isEmpty()){
				String message = AppHelper.getBundleMessage("error.usuario.sinMovimiento");
				MessageUtil.showMessageInDialogError("Error",message);
			} else {
				//Llamar al listado de solicitudes
				//this.redirect("project-client-web/administration/solicitud/index.jsf");
				//NavigationRulezHelper.redirect("/project-client-web/administration/solicitud/index.jsf");
				
				String uri = navigationRulez.buildUri("/project-client-web/administration/solicitud/index.jsf");
				redirect(uri);
			}*/
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
        
}
