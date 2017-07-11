/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.data;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import py.com.app.model.Persona;

/**
 *
 * @author Lorena Franco
 */
@Stateless
public class PersonaFacade extends AbstractFacade<Persona> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }
    
    public List<Persona> findAllActives() {
        return em.createQuery("Select c from  Persona c Where c.estado = 'AC'").getResultList();
    }
}
