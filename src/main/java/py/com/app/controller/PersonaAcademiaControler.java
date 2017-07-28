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

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import py.com.app.data.ConcursoAcademiaCoreoFacade;
import py.com.app.data.PersonaAcademiaFacade;
import py.com.app.model.Persona;
import py.com.app.model.PersonaAcademia;
import py.com.app.util.AbstractController;
import py.com.app.util.AppHelper;
import py.com.app.util.CalendarHelper;
import py.com.app.util.Credentials;
import py.com.app.util.MessageUtil;
import py.com.app.util.NavigationRulezHelper;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6

@SessionScoped
@ManagedBean
public class PersonaAcademiaControler extends AbstractController<PersonaAcademia> implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2838146797217857346L;

	@Inject
    private FacesContext facesContext;

    @EJB
    private PersonaAcademiaFacade service;

    @Inject
    Credentials credentials;

    @EJB
    private ConcursoAcademiaCoreoFacade serviceAcademiaCoreoFacade;

    public PersonaAcademiaControler() {
        super(PersonaAcademia.class);
    }

    @PostConstruct
    public void init() {
        super.setService(service);
        
        if (this.credentials == null || this.credentials.getIdEmpleado() == null) {
	        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información!", "Es necesario volver a Loguearse!");
	        facesContext.addMessage(null, m);
	        return;
        }

    }

    @Override
    public PersonaAcademia prepareCreate(ActionEvent event) {
    	super.prepareCreate(event);
    	this.getSelected().setPersona(new Persona());
        return this.getSelected();
    }
    	
	public void nuevo(ActionEvent event) {
    	this.prepareCreate(event);
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/personaAcademiaGral/personaAcademiaAdd.xhtml");
	}
	
	public void edit() {
        NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/personaAcademiaGral/personaAcademiaAdd.xhtml");
	}

    public void confirm(String accion){
    	
        try {
        	
	        if(accion.equals("new")){
	        
                this.getSelected().setFechaInicio(CalendarHelper.getCurrentTimestamp());
                this.getSelected().setEstado("AC");
                this.getSelected().setUsuAlta(credentials.getUsername());
                this.getSelected().setFechaAlta(CalendarHelper.getCurrentTimestamp());
                
                this.getSelected().getPersona().setEstado("AC");
                this.getSelected().getPersona().setUsuAlta(credentials.getUsername());
                this.getSelected().getPersona().setFechaAlta(CalendarHelper.getCurrentTimestamp());
	            
	            this.getSelected().getPersona().setUsuMod(credentials.getUsername());
	            this.getSelected().getPersona().setFechaMod(CalendarHelper.getCurrentTimestamp());
	
	            this.getSelected().setUsuMod(credentials.getUsername());
	            this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
	            
	
	            this.saveNew(null);
	        }else {
	        	
		        if(accion.equals("edit")){

		            this.getSelected().getPersona().setUsuMod(credentials.getUsername());
		            this.getSelected().getPersona().setFechaMod(CalendarHelper.getCurrentTimestamp());
		
		            this.getSelected().setUsuMod(credentials.getUsername());
		            this.getSelected().setFechaMod(CalendarHelper.getCurrentTimestamp());
		            
		            this.update();

		        }else {
		        	
		        	this.getSelected().setEstado("IN");
		        	this.delete(null);
		        	
		        }
	            
	        }
        
        
            NavigationRulezHelper.redirect(AppHelper.getDomainUrl() + "/pages/personaAcademiaGral/personaAcademiaList.xhtml");
            
        }catch (Exception e) {
        	
            MessageUtil.addFacesMessageInfo("msg.atencion", AppHelper.getBundleMessage("msg.deletedfail"));
		}
        
                	
    }

}
