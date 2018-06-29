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

import py.com.app.model.ConcursoTipoParticipacion;
import py.com.app.model.TipoParticipacion;

/**
 *
 * @author Lorena Franco
 */
@Stateless
public class TipoParticipacionFacade extends AbstractFacade<TipoParticipacion> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoParticipacionFacade() {
        super(TipoParticipacion.class);
    }
    
    public List<TipoParticipacion> findAllActives() {
        return em.createQuery("Select c from  TipoParticipacion c Where c.estado = 'AC'").getResultList();
    }

    public List<ConcursoTipoParticipacion> findAllConcursoActives() {
        return em.createQuery("Select c from  ConcursoTipoParticipacion c "
        		+ "Where c.estado = 'AC' and c.concurso.id in (select o.id from Concurso o where o.vigente is true)").getResultList();
    }


    
}
