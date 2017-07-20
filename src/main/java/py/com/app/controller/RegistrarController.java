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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import py.com.app.data.UsuarioFacade;
import py.com.app.model.Academia;
import py.com.app.model.Usuario;
import py.com.app.util.CalendarHelper;
import py.com.app.util.EmailUtils;
import py.com.app.util.Mensaje;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6

@Model
public class RegistrarController implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5281220660955050842L;

	@Inject
    private FacesContext facesContext;

    @Inject
    private UsuarioFacade usuarioService;

    @Produces
    @Named
    private Usuario usuario;

    private ArrayList<Academia> academiaList;
    
	@PostConstruct
    public void init() {
		usuario = new Usuario();
    }

    public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		String email = (String) value;
		if(usuarioService.verificaEmail(email)){
			FacesContext.getCurrentInstance().addMessage("validaEmail", new FacesMessage("Este email ya fue registrado"));
			throw new ValidatorException(new FacesMessage("Este email ya fue registrado"));
		}
    }

    public void register() throws Exception {
    	
		if(usuarioService.verificaEmail(usuario.getMail())){
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "validaEmail!", "Este email ya fue registrado");
            facesContext.addMessage(null, m);
            return;
		}

		if(usuarioService.verificaUsername(usuario.getUsername())){
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "validaEmail!", "Este usuario ya fue registrado");
            facesContext.addMessage(null, m);
            return;
		}
    	
        try {
        	usuario.setEstado("AC");
            usuario.setUsuAlta(usuario.getUsername());
            usuario.setFechaAlta(CalendarHelper.getCurrentTimestamp());
            usuario.setUsuMod(usuario.getUsername());
            usuario.setFechaMod(CalendarHelper.getCurrentTimestamp());
            if (usuario.getAcademia().getId() != null)
            	usuario.setActivo(Boolean.TRUE);
            else
            	usuario.setActivo(Boolean.FALSE);
            usuario.setBloqueado(Boolean.FALSE);

    		usuarioService.create(usuario);
    		
    		try {
    			Mensaje mensaje = new Mensaje("Nuevo Usuario Registrado " + usuario.getUsername());
    			mensaje.setDestino(usuario.getMail());
    			EmailUtils.enviaEmail(mensaje);
    		}catch (Exception e) {
    			e.printStackTrace();
			}
    		
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful, Revisa tu email!");
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

	public ArrayList<Academia> getAcademiaList() {
		return academiaList;
	}

	public void setAcademiaList(ArrayList<Academia> academiaList) {
		this.academiaList = academiaList;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
