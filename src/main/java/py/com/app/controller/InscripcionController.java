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
public class InscripcionController extends AbstractController<ConcursoAcademiaCoreo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7861772678272078184L;

	@Inject
    private FacesContext facesContext;

    @EJB
    private ConcursoAcademiaCoreoFacade service;

    @Inject
    Credentials credentials;

    private Concurso concurso;
    private List<Persona>  listParticipatesAcademia;

    private List<ConcursoAcademiaCoreoParticipantes>  listCoreoParticipante;
    private List<ConcursoAcademiaCoreoParticipantes>  listCoreoParticipanteRemove;
    
    private ConcursoAcademiaCoreoParticipantes participante;
    
    private List<ConcursoAcademiaCoreo> list;
    
    private Double total;

    public InscripcionController() {
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

        concurso = service.findConcursoVigente();
        this.list = service.findAllInscripciones(concurso.getId());

        getterTotal();
	}

	public void nuevaInscripcion(ActionEvent event) {
		
    	this.prepareCreate(event);
		
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/inscripcion/inscripcionAdd.xhtml");
	}
	
	public void editInscripcion() {
        participante = new ConcursoAcademiaCoreoParticipantes();
        participante.setIdPersona(new Persona());

        this.listCoreoParticipante = (List<ConcursoAcademiaCoreoParticipantes>) service.findAllParticipante(this.getSelected().getId());
        this.listCoreoParticipanteRemove = new ArrayList<ConcursoAcademiaCoreoParticipantes>();

        if (listCoreoParticipante == null) listCoreoParticipante = new ArrayList<ConcursoAcademiaCoreoParticipantes>();
        returnListParticipantesAcademia();
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/inscripcion/inscripcionAdd.xhtml");
	}
	
    @Override
    public ConcursoAcademiaCoreo prepareCreate(ActionEvent event) {
    	super.prepareCreate(event);
    	
    	this.getSelected().setConcursoAcademia(new ConcursoAcademia());
    	this.getSelected().getConcursoAcademia().setAcademia(new Academia());
        this.getSelected().setCategoria(new Categoria());
        this.getSelected().setModalidad(new ConcursoModalidad());
        this.getSelected().setPersona(new Persona());
        this.getSelected().setTipoParticipacion(new ConcursoTipoParticipacion());

        participante = new ConcursoAcademiaCoreoParticipantes();
        participante.setIdPersona(new Persona());
        
        listCoreoParticipante = new ArrayList<ConcursoAcademiaCoreoParticipantes>();
        listCoreoParticipanteRemove = new ArrayList<ConcursoAcademiaCoreoParticipantes>();
        
        return this.getSelected();
        
    }

    public void confirm(String accion){
    	
            if(accion.equals("new")){
            	
	            	if (listCoreoParticipante.size() < 1) {
	                    FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información!", "No se ha registrado ningun participante");
	                    facesContext.addMessage(null, m);
	                    return;
	            	}
	            	
	            	ConcursoAcademia concursoAcademia = service.getConcursoAcademia(this.getSelected().getConcursoAcademia().getAcademia().getId(),concurso.getId());
	            	
	                if (concursoAcademia == null) {
	            		concursoAcademia = new ConcursoAcademia();
	            		concursoAcademia.setConcurso(concurso);
	            		concursoAcademia.setAcademia(this.getSelected().getConcursoAcademia().getAcademia());
	            		service.guardarConcursoAcademia(concursoAcademia);     
		            	concursoAcademia = service.getConcursoAcademia(this.getSelected().getConcursoAcademia().getAcademia().getId(),concurso.getId());
	            	}
	                
	            	this.getSelected().setConcursoAcademia(concursoAcademia);
	            	
           			this.getSelected().setEstado("AC");
                    this.getSelected().setUsuAlta(credentials.getUsername());
                    this.getSelected().setFechaAlta(CalendarHelper.getCurrentTimestamp());
                    this.getSelected().setUsuMod(credentials.getUsername());
                    this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
                    this.saveNew(null);
                    
                	for (ConcursoAcademiaCoreoParticipantes p : listCoreoParticipante) {
                		p.setIdAcademiaConcursoCoreo(this.getSelected());
               			service.updateParticipante(p);
        			}

	            	for (ConcursoAcademiaCoreoParticipantes p : listCoreoParticipanteRemove) {
	            		if (p.getId() != null)
	            			service.removeParticipante(p);
	    			}
            	
            }else{
                    if(accion.equals("edit")){

                		this.getSelected().setUsuMod(credentials.getUsername());
                        this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
                        this.save(null);
                        
                    	for (ConcursoAcademiaCoreoParticipantes p : listCoreoParticipante) {
                   			service.updateParticipante(p);
            			}
                		
                    	for (ConcursoAcademiaCoreoParticipantes p : listCoreoParticipanteRemove) {
                   			service.removeParticipante(p);
            			}
                    	

                            
                    }else{
                            this.getSelected().setEstado("IN");
                            this.delete(null);
                    }
            }

            this.list = service.findAllInscripciones(concurso.getId());
            getterTotal();
            NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/inscripcion/inscripcionList.xhtml");

    }
    
    public void getterTotal(){
    	total = 0.0;
    	for (ConcursoAcademiaCoreo c : this.list) {
        	total += c.getTotal();		
		}
    }
    
    public void returnListParticipantesAcademia() { 
    	try {
    		
    		this.listParticipatesAcademia = service.findParticipantesAcademia(this.getSelected().getConcursoAcademia().getAcademia().getId());
    	}catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    }

    public void eliminarParticipante(ConcursoAcademiaCoreoParticipantes detalle) throws Exception
    {    	
        if(detalle.getId() != null)
        {
        	listCoreoParticipanteRemove.add(detalle);
        }
        listCoreoParticipante.remove(detalle);
        participante = null;
    }

    public void createNew() {
    	
    	for (ConcursoAcademiaCoreoParticipantes p : listCoreoParticipante) {
			if (p.getIdPersona().getId() == participante.getIdPersona().getId()) {
	            FacesMessage msg = new FacesMessage("Dublicado", "Este participante ya fue incluido");
	            FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
		}

       	participante.setIdAcademiaConcursoCoreo(this.getSelected());
       	listCoreoParticipante.add(participante);
       	participante = new ConcursoAcademiaCoreoParticipantes();
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

	public List<ConcursoAcademiaCoreoParticipantes> getListCoreoParticipante() {
		return listCoreoParticipante;
	}

	public void setListCoreoParticipante(List<ConcursoAcademiaCoreoParticipantes> listCoreoParticipante) {
		this.listCoreoParticipante = listCoreoParticipante;
	}

	public ConcursoAcademiaCoreoParticipantes getParticipante() {
		return participante;
	}

	public void setParticipante(ConcursoAcademiaCoreoParticipantes participante) {
		this.participante = participante;
	}

	public List<Persona> getListParticipatesAcademia() {
		return listParticipatesAcademia;
	}

	public void setListParticipatesAcademia(List<Persona> listParticipatesAcademia) {
		this.listParticipatesAcademia = listParticipatesAcademia;
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
