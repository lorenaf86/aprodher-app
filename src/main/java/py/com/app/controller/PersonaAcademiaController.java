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

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import py.com.app.data.ConcursoAcademiaCoreoFacade;
import py.com.app.data.PersonaAcademiaFacade;
import py.com.app.model.Academia;
import py.com.app.model.Persona;
import py.com.app.model.PersonaAcademia;
import py.com.app.util.CalendarHelper;
import py.com.app.util.Credentials;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6

@Model
public class PersonaAcademiaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2135255041232804226L;

	@Inject
    private FacesContext facesContext;

    @Inject
    private PersonaAcademiaFacade service;

    @Produces
    @Named
    private PersonaAcademia personaAcademia;

    @Inject
    Credentials credentials;

    @Inject
    private ConcursoAcademiaCoreoFacade serviceAcademiaCoreoFacade;

    private ArrayList<PersonaAcademia> personaAcademiaList;
    
	@PostConstruct
    public void init() {
        personaAcademia = new PersonaAcademia();
        personaAcademia.setPersona(new Persona());
        personaAcademiaList = (ArrayList<PersonaAcademia>) service.findAll();
    }

    public void register() throws Exception {
        try {
        	
            Academia academia = serviceAcademiaCoreoFacade.findAcademia(Integer.parseInt(this.credentials.getIdEmpleado()+""));

   			personaAcademia.getPersona().setEstado("AC");
            personaAcademia.getPersona().setUsuAlta(credentials.getUsername());
            personaAcademia.getPersona().setFechaAlta(CalendarHelper.getCurrentTimestamp());
            personaAcademia.getPersona().setUsuMod(credentials.getUsername());
            personaAcademia.getPersona().setFechaMod(CalendarHelper.getCurrentTimestamp());

            personaAcademia.setEstado("AC");
            personaAcademia.setUsuAlta(credentials.getUsername());
            personaAcademia.setFechaAlta(CalendarHelper.getCurrentTimestamp());
            personaAcademia.setUsuMod(credentials.getUsername());
            personaAcademia.setFechaMod(CalendarHelper.getCurrentTimestamp());
            
            personaAcademia.setAcademia(academia);
            service.create(personaAcademia);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
            init();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
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

	public PersonaAcademia getPersonaAcademia() {
		return personaAcademia;
	}

	public void setPersonaAcademia(PersonaAcademia academia) {
		this.personaAcademia = academia;
	}

	public ArrayList<PersonaAcademia> getPersonaAcademiaList() {
		return personaAcademiaList;
	}

	public void setPersonaAcademiaList(ArrayList<PersonaAcademia> academiaList) {
		this.personaAcademiaList = academiaList;
	}

}
