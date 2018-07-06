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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import py.com.app.data.OrdenFacade;
import py.com.app.model.ConcursoAcademiaCoreo;
import py.com.app.util.Credentials;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6

@Model
public class InformeController implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5281220660955050842L;

	@Inject
    private FacesContext facesContext;

    @Inject
    private OrdenFacade ordenService;

    @Inject
    Credentials credentials;

    private ArrayList list;

    private Double total;

    @PostConstruct
    public void init() {
    	
        if (this.credentials == null || this.credentials.getIdEmpleado() == null) {
	        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información!", "Es necesario volver a Loguearse!");
	        facesContext.addMessage(null, m);
	        return;
        }

        if (Integer.parseInt(this.credentials.getIdEmpleado()+"") == 1)
        	list = (ArrayList) ordenService.findAllTotal();
        else
        	list = (ArrayList) ordenService.findAllTotal(Integer.parseInt(this.credentials.getIdEmpleado()+""));
        
        getterTotal();
    }
	
    public void getterTotal(){
    	total = 0.0;
    	for (Object c : this.list) {
    		Object[] o = (Object[]) c;
        	total += (Double) o[2];		
		}
    }

    public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}


	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
