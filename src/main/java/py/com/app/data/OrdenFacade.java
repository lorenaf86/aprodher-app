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

import py.com.app.model.ConcursoAcademiaCoreo;

/**
 *
 * @author Lorena Franco
 */
@Stateless
public class OrdenFacade {

    @Inject
    private EntityManager em;

    public List<ConcursoAcademiaCoreo> findAll() {
        return em.createQuery("Select c from ConcursoAcademiaCoreo c "
                + " order by c.categoria.id, c.modalidad.id, c.tipoParticipacion.id").getResultList();
    }
    
}
