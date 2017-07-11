package py.com.app.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;

import py.com.app.data.CategoriaFacade;
import py.com.app.data.ConcursoAcademiaFacade;
import py.com.app.data.ModalidadFacade;
import py.com.app.data.PersonaFacade;
import py.com.app.data.TipoParticipacionFacade;
import py.com.app.model.Academia;
import py.com.app.model.Categoria;
import py.com.app.model.Concurso;
import py.com.app.model.ConcursoAcademia;
import py.com.app.model.ConcursoAcademiaCoreo;
import py.com.app.model.ConcursoAcademiaCoreoParticipantes;
import py.com.app.model.Modalidad;
import py.com.app.model.Persona;
import py.com.app.model.PersonaAcademia;
import py.com.app.model.TipoParticipacion;
import py.com.app.util.AbstractController;
import py.com.app.util.AppHelper;
import py.com.app.util.CalendarHelper;
import py.com.app.util.Credentials;
import py.com.app.util.NavigationRulezHelper;


@SessionScoped
@ManagedBean
public class InscripcionController extends AbstractController<ConcursoAcademia> implements Serializable {

    @EJB
    private ConcursoAcademiaFacade service;
    @EJB
    private CategoriaFacade serviceCategoria;
    @EJB
    private ModalidadFacade serviceModalidad;
    @EJB
    private TipoParticipacionFacade serviceTipoParticipacion;
    @EJB
    private PersonaFacade servicePersona;
    
	    
    private List<ConcursoAcademia>  list;
    
    private Academia academia;
    private Concurso concurso;
    private ConcursoAcademiaCoreo coreografia;

    private ArrayList categoriaList;
    private ArrayList modalidadList;
    private ArrayList tipoParticipacionList;
    private ArrayList personaList;

    private ArrayList participanteList;
    private PersonaAcademia participante;
    
    private ArrayList removeCollectionParticipante;

    @Inject
    Credentials credentials;

    public InscripcionController() {
        super(ConcursoAcademia.class);
    }

    @PostConstruct
    public void init() {
        
        super.setService(service);
        
        academia = service.findAcademia(this.credentials.getIdEmpleado());
        concurso = service.findConcursoVigente();
        
        this.list = service.findAllInscripciones(academia.getId());
        inicializar();
    
      //  this.getSelected().setConcurso(concurso);
      //  this.getSelected().setIdAcademia(academia);
        
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
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/inscripcion/index.xhtml");
    }
    
    private void inicializar(){
        this.coreografia = new ConcursoAcademiaCoreo();
        this.coreografia.setCategoria(new Categoria());
        this.coreografia.setModalidad(new Modalidad());
        this.coreografia.setPersona(new Persona());
        this.coreografia.setTipoParticipacion(new TipoParticipacion());
        
        this.participante = new PersonaAcademia();
        this.participanteList = new ArrayList();
        
    }
    
    public void prepareNew() {
        super.prepareCreate();
        inicializar();
        
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/addInscripcion/View.xhtml");

//        this.setWizardTitle(this.selectedSolicitudConcepto.getNombre() + " (Agregar)");
        //  RequestContext.getCurrentInstance().execute("PF('inscripcionCreateDialog').show();");
    }
    
    public void eliminarParticipante(ConcursoAcademiaCoreoParticipantes detalle)
    {    	
        if(detalle.getId() != null)
        {
            removeCollectionParticipante.add(detalle);
        }
        participanteList.remove(detalle);
        participante = null;
    }

    public void nuevoDetalle(){
          this.participante = new PersonaAcademia();
          this.participante.setAcademia(academia);
          
          RequestContext.getCurrentInstance().execute("PF('addParticipanteCreateDlg').show();");
    }

    public void addDetalle(){
        ConcursoAcademiaCoreoParticipantes detalle = new ConcursoAcademiaCoreoParticipantes();
        detalle.setIdPersona(this.participante.getIdPersona());
        participanteList.add(detalle);
    }
    
    public ArrayList getCategoriaList() {
        return (ArrayList) serviceCategoria.findAllActives();
    }
    
    public ArrayList getPersonaList() {
        return (ArrayList) servicePersona.findAllActives();
    }

    public ArrayList getModalidadList() {
        return (ArrayList) serviceModalidad.findAllActives();
    }

    public ArrayList getTipoParticipacionList() {
        return (ArrayList) serviceTipoParticipacion.findAllActives();
    }
    
    public String onFlowProcess(FlowEvent event) {
            return event.getNewStep();
    }
    
    public Credentials getCredentials() {
            return credentials;
    }

    public List<ConcursoAcademia> getList() {
            return list;
    }

    public void setList(List<ConcursoAcademia> list) {
            this.list = list;
    }

    public ConcursoAcademiaFacade getService() {
            return service;
    }

    public void setService(ConcursoAcademiaFacade service) {
            this.service = service;
    }

    @Override
    public List<ConcursoAcademia> getItems()
    {
        return super.getItems();
    }

    public Academia getAcademia() {
        return academia;
    }

    public void setAcademia(Academia academia) {
        this.academia = academia;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

    public ConcursoAcademiaCoreo getCoreografia() {
        return coreografia;
    }

    public void setCoreografia(ConcursoAcademiaCoreo coreografia) {
        this.coreografia = coreografia;
    }
        
    public ArrayList getParticipanteList() {
        return participanteList;
    }

    public void setParticipanteList(ArrayList participantes) {
        this.participanteList = participantes;
    }

    public PersonaAcademia getParticipante() {
        return participante;
    }

    public void setParticipante(PersonaAcademia participante) {
        this.participante = participante;
    }

}
