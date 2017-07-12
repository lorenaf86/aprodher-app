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
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import py.com.app.data.ConcursoAcademiaFacade;
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
import py.com.app.util.AppHelper;
import py.com.app.util.Credentials;
import py.com.app.util.NavigationRulezHelper;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6

@Model
public class InscripcionControler implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8409480562667328805L;

	@Inject
    private FacesContext facesContext;

    @Inject
    private ConcursoAcademiaFacade service;

    @Inject
    Credentials credentials;

    @Produces
    @Named
    private ConcursoAcademiaCoreo selected;
    
    private Concurso concurso;
    private Academia academia;
    private ConcursoAcademia concursoAcademia;
    private Persona participante;
    
    private List<ConcursoAcademiaCoreo>  list;

    private ArrayList<Persona> participantesList;
    private ArrayList<Persona> removeParticipanteList;

    
	@PostConstruct
    public void init() {

        academia = service.findAcademia((long) 1);//this.credentials.getIdEmpleado()
        concurso = service.findConcursoVigente();
        
    	concursoAcademia = service.getConcursoAcademia(academia.getId(),concurso.getId());
    	
        inicializar();

        if (concursoAcademia == null) {
    		concursoAcademia = new ConcursoAcademia();
    		concursoAcademia.setConcurso(concurso);
    		concursoAcademia.setIdAcademia(academia);
    	}
    	
    	this.selected.setConcursoAcademia(concursoAcademia);
    	
        this.list = service.findAllInscripciones(concursoAcademia.getId());

	}

    private void inicializar(){
        this.selected = new ConcursoAcademiaCoreo();
        this.selected.setCategoria(new Categoria());
        this.selected.setModalidad(new Modalidad());
        this.selected.setPersona(new Persona());
        this.selected.setTipoParticipacion(new TipoParticipacion());
        
        this.participantesList = new ArrayList<Persona>();
        
        this.participante = new Persona();
        
    }

    public void register() throws Exception {
    	
    	if (participantesList.isEmpty()) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información!", "No se ha registrado ningun participante");
            facesContext.addMessage(null, m);
            return;
    	}
    	
        try {
        	
        	
        	if (concursoAcademia.getId() != null) { 
        		concursoAcademia = service.updateReturnObject(concursoAcademia);        		 
        	}

        	this.selected.setConcursoAcademia(concursoAcademia);
        	
        	ArrayList<ConcursoAcademiaCoreoParticipantes> listParticipantes = new ArrayList<ConcursoAcademiaCoreoParticipantes>();
        	
        	for (Persona p : removeParticipanteList) {
        		ConcursoAcademiaCoreoParticipantes part = service.findParticipante(p.getId());
        		if (part!=null) service.removeParticipante(part);
			}
        	
        	for (Persona p : participantesList) {
        		ConcursoAcademiaCoreoParticipantes newP = new ConcursoAcademiaCoreoParticipantes();
        		newP.setIdPersona(p);
        		newP.setIdAcademiaConcursoCoreo(this.selected);
        		
        		listParticipantes.add(newP);
			}

        	this.selected.setConcursoAcademiaCoreoParticipantesList(listParticipantes);
        	
       		service.updateCoreografia(this.selected);

        	//academiaService.create(academia);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
            init();
            
            NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/addInscripcion/inscripcionList.xhtml");

        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    public void eliminarParticipante(Persona detalle) throws Exception
    {    	
        if(detalle.getId() != null)
        {
        	removeParticipanteList.add(detalle);
        }
        participantesList.remove(detalle);
        participante = null;
    }

    public void addDetalle(){
    	try {
    		participantesList.add(this.participante);
    	}catch (Exception e) {
    		e.printStackTrace();
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

	public ConcursoAcademiaCoreo getSelected() {
		return selected;
	}

	public void setSelected(ConcursoAcademiaCoreo selected) {
		this.selected = selected;
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

	public List<ConcursoAcademiaCoreo> getList() {
		return list;
	}

	public void setList(List<ConcursoAcademiaCoreo> list) {
		this.list = list;
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

	public ArrayList<Persona> getRemoveParticipanteList() {
		return removeParticipanteList;
	}

	public void setRemoveParticipanteList(ArrayList<Persona> removeParticipanteList) {
		this.removeParticipanteList = removeParticipanteList;
	}


}
