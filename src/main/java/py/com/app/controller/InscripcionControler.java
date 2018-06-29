/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package py.com.app.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import py.com.app.data.ConcursoAcademiaCoreoFacade;
import py.com.app.model.Academia;
import py.com.app.model.Categoria;
import py.com.app.model.Concurso;
import py.com.app.model.ConcursoAcademia;
import py.com.app.model.ConcursoAcademiaCoreo;
import py.com.app.model.ConcursoAcademiaCoreoParticipantes;
import py.com.app.model.ConcursoModalidad;
import py.com.app.model.ConcursoTipoParticipacion;
import py.com.app.model.Persona;
import py.com.app.util.AbstractController;
import py.com.app.util.AppHelper;
import py.com.app.util.CalendarHelper;
import py.com.app.util.Credentials;
import py.com.app.util.NavigationRulezHelper;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6

@SessionScoped
@ManagedBean
public class InscripcionControler extends AbstractController<ConcursoAcademiaCoreo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3460780110361394289L;

	@Inject
    private FacesContext facesContext;

    @EJB
    private ConcursoAcademiaCoreoFacade service;

    @Inject
    Credentials credentials;

    private Concurso concurso;
    private Academia academia;
    private ConcursoAcademia concursoAcademia;
    private Persona participante;
    
    private List<ConcursoAcademiaCoreo>  listCoreo;
    private List<Persona>  listParticipatesAcademia;

    private ArrayList<Persona> participantesList;

    private List<ConcursoAcademiaCoreoParticipantes>  listCoreoParticipante;
    private List<ConcursoAcademiaCoreoParticipantes>  listCoreoParticipanteRemove;
    
    private ConcursoAcademiaCoreoParticipantes participanteEdit;

    private List<ConcursoAcademiaCoreo> list;

    private Double total=0.0;

    public InscripcionControler() {
        super(ConcursoAcademiaCoreo.class);
    }

    @PostConstruct
    public void init() {

        super.setService(service);
        
        if (this.credentials == null || this.credentials.getIdEmpleado() == null) {
	        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información!", "Es necesario volver a Loguearse!");
	        facesContext.addMessage(null, m);
	        return;
        }

        
        academia = service.findAcademia(Integer.parseInt(this.credentials.getIdEmpleado()+""));
        concurso = service.findConcursoVigente();
        
    	concursoAcademia = service.getConcursoAcademia(academia.getId(),concurso.getId());
    	
        if (concursoAcademia == null) {
    		concursoAcademia = new ConcursoAcademia();
    		concursoAcademia.setConcurso(concurso);
    		concursoAcademia.setAcademia(academia);
    	}
    	
    	
        this.listCoreo = service.findAllInscripciones(concursoAcademia.getId());
        
        this.listParticipatesAcademia = service.findParticipantesAcademia(academia.getId());

        getterTotal();

	}

    public void getterTotal(){
    	total = 0.0;
    	for (ConcursoAcademiaCoreo c : listCoreo) {
        	total += c.getTotal();		
		}
    }
    
	public void nuevaInscripcion(ActionEvent event) {
		
    	this.prepareCreate(event);
		
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/addInscripcion/inscripcionAdd.xhtml");
	}
	
	public void editInscripcion() {
        if (listCoreoParticipante == null) listCoreoParticipante = new ArrayList<ConcursoAcademiaCoreoParticipantes>();
        this.listCoreoParticipante = (List<ConcursoAcademiaCoreoParticipantes>) service.findAllParticipante(this.getSelected().getId());
        this.listCoreoParticipanteRemove = new ArrayList<ConcursoAcademiaCoreoParticipantes>();
        this.participanteEdit = new ConcursoAcademiaCoreoParticipantes();
        this.participanteEdit.setIdPersona(new Persona());
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/addInscripcion/inscripcionEdit.xhtml");
	}
	
    @Override
    public ConcursoAcademiaCoreo prepareCreate(ActionEvent event) {
    	super.prepareCreate(event);
    	
    	this.getSelected().setConcursoAcademia(concursoAcademia);
        this.getSelected().setCategoria(new Categoria());
        this.getSelected().setModalidad(new ConcursoModalidad());
        this.getSelected().setPersona(new Persona());
        this.getSelected().setTipoParticipacion(new ConcursoTipoParticipacion());
        
        this.participantesList = new ArrayList<Persona>();
        this.participante = new Persona();
        
        return this.getSelected();
        
    }

    public void confirm(String accion){
    	
            if(accion.equals("new")){
            	
            	if (participantesList.size() < 1) {
                    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información!", "No se ha registrado ningun participante");
                    facesContext.addMessage(null, m);
                    return;
            	}
            	

            	if (concursoAcademia.getId() == null) { 
            		service.guardarConcursoAcademia(concursoAcademia);     
                	concursoAcademia = service.getConcursoAcademia(academia.getId(),concurso.getId());
            	} 

            	this.getSelected().setConcursoAcademia(concursoAcademia);
            	
        		//this.getSelected().setConcursoAcademiaCoreoParticipantesList(listParticipantes);
        	
       			this.getSelected().setEstado("AC");
                this.getSelected().setUsuAlta(credentials.getUsername());
                this.getSelected().setFechaAlta(CalendarHelper.getCurrentTimestamp());
                this.getSelected().setUsuMod(credentials.getUsername());
                this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
                
                this.saveNew(null);
                
            	for (Persona p : participantesList) {
            		ConcursoAcademiaCoreoParticipantes newP = new ConcursoAcademiaCoreoParticipantes();
            		newP.setIdPersona(p);
            		newP.setIdAcademiaConcursoCoreo(this.getSelected());
           			service.updateParticipante(newP);
    			}


            }else{
                    if(accion.equals("edit")){

                		this.getSelected().setUsuMod(credentials.getUsername());
                        this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
                        
                    	for (ConcursoAcademiaCoreoParticipantes p : listCoreoParticipante) {
                   			service.updateParticipante(p);
            			}
                		
                    	for (ConcursoAcademiaCoreoParticipantes p : listCoreoParticipanteRemove) {
                   			service.removeParticipante(p);
            			}
                    	
                        this.save(null);
                    	
                            
                    }else{
                            this.getSelected().setEstado("IN");
                            this.delete(null);
                    }
            }

            this.listCoreo = service.findAllInscripciones(concursoAcademia.getId());
            NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/addInscripcion/inscripcionList.xhtml");
    }

    public void eliminarParticipanteEdit(ConcursoAcademiaCoreoParticipantes detalle) throws Exception
    {    	
        if(detalle.getId() != null)
        {
        	listCoreoParticipanteRemove.add(detalle);
        }
        listCoreoParticipante.remove(detalle);
        participanteEdit = null;
    }

    public void createNew() {
        if(participantesList.contains(participante)) {
            FacesMessage msg = new FacesMessage("Dublicado", "Este participante ya fue incluido");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } 
        else {
        	participantesList.add(participante);
            participante = new Persona();
        }
    }
     

    public void eliminarParticipante(Persona detalle) throws Exception
    {    	
        participantesList.remove(detalle);
        participante = null;
    }

    public void createNewEdit() {
        if(listCoreoParticipante.contains(participanteEdit)) {
            FacesMessage msg = new FacesMessage("Dublicado", "Este participante ya fue incluido");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } 
        else {
        	participanteEdit.setIdAcademiaConcursoCoreo(this.getSelected());
        	listCoreoParticipante.add(participanteEdit);
        	participanteEdit = new ConcursoAcademiaCoreoParticipantes();
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

	public Concurso getConcurso() {
		return concurso;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
	}

	public Academia getAcademia() {
		return academia;
	}

	public void setAcademia(Academia academia) {
		this.academia = academia;
	}

	public Persona getParticipante() {
		return participante;
	}

	public void setParticipante(Persona participante) {
		this.participante = participante;
	}

	public List<ConcursoAcademiaCoreo> getListCoreo() {
		return listCoreo;
	}

	public void setListCoreo(List<ConcursoAcademiaCoreo> listCoreo) {
		this.listCoreo = listCoreo;
	}

	public ConcursoAcademia getConcursoAcademia() {
		return concursoAcademia;
	}

	public void setConcursoAcademia(ConcursoAcademia concursoAcademia) {
		this.concursoAcademia = concursoAcademia;
	}

	public ArrayList<Persona> getParticipantesList() {
		return participantesList;
	}

	public void setParticipantesList(ArrayList<Persona> participantesList) {
		this.participantesList = participantesList;
	}

	public List<Persona> getListParticipatesAcademia() {
		return listParticipatesAcademia;
	}

	public void setListParticipatesAcademia(List<Persona> listParticipatesAcademia) {
		this.listParticipatesAcademia = listParticipatesAcademia;
	}

	public List<ConcursoAcademiaCoreoParticipantes> getListCoreoParticipante() {
		return listCoreoParticipante;
	}

	public void setListCoreoParticipante(List<ConcursoAcademiaCoreoParticipantes> listCoreoParticipante) {
		this.listCoreoParticipante = listCoreoParticipante;
	}

	public ConcursoAcademiaCoreoParticipantes getParticipanteEdit() {
		return participanteEdit;
	}

	public void setParticipanteEdit(ConcursoAcademiaCoreoParticipantes participanteEdit) {
		this.participanteEdit = participanteEdit;
	}


	public List<ConcursoAcademiaCoreo> getList() {
		return list;
	}

	public void setList(List<ConcursoAcademiaCoreo> list) {
		this.list = list;
	}


	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
