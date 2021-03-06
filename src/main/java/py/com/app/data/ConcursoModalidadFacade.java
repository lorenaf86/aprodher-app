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

import py.com.app.model.ConcursoModalidad;

/**
 *
 * @author Lorena Franco
 */
@Stateless
public class ConcursoModalidadFacade extends AbstractFacade<ConcursoModalidad> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConcursoModalidadFacade() {
        super(ConcursoModalidad.class);
    }
    
    public List<ConcursoModalidad> findAllActives() {
        return em.createQuery("Select c from  ConcursoModalidad c "
        		+ "Where c.estado = 'AC' and c.concurso.id in (select o.id from Concurso o where o.vigente is true)").getResultList();
    }

}
