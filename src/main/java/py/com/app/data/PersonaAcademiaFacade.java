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

import py.com.app.model.PersonaAcademia;

/**
 *
 * @author Lorena Franco
 */
@Stateless
public class PersonaAcademiaFacade extends AbstractFacade<PersonaAcademia> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaAcademiaFacade() {
        super(PersonaAcademia.class);
    }
    
	public List<PersonaAcademia> findParticipantesAcademia(Integer id) {
		List list = getEntityManager().createQuery("Select c from PersonaAcademia c "
                + " Where c.academia.id = " + id).getResultList();
        if (list.isEmpty()) return null;
        else return list;
	}

}

