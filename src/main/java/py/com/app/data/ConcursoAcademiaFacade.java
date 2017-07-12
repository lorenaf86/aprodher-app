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

import py.com.app.model.Academia;
import py.com.app.model.Concurso;
import py.com.app.model.ConcursoAcademia;
import py.com.app.model.ConcursoAcademiaCoreo;
import py.com.app.model.ConcursoAcademiaCoreoParticipantes;
import py.com.app.model.Usuario;

/**
 *
 * @author Lorena Franco
 */
@Stateless
public class ConcursoAcademiaFacade extends AbstractFacade<ConcursoAcademia> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConcursoAcademiaFacade() {
        super(ConcursoAcademia.class);
    }
    
    public Academia findAcademia(Long idUsuario){
       
       Integer id = ((Usuario)em.find(Usuario.class, idUsuario)).getIdAcademia();
        
       return em.find(Academia.class, id);
    }
    
    public List<ConcursoAcademiaCoreo> findAllInscripciones(Integer idAcademia) {
        return getEntityManager().createQuery("Select c from ConcursoAcademiaCoreo c "
                + " Where c.concursoAcademia.id = " + idAcademia).getResultList();
    }
    
    public Concurso findConcursoVigente(){
        List list = getEntityManager().createQuery("Select c from Concurso c "
                + " Where c.vigente = true Order by c.fecha desc ").getResultList();
        
        if (list.size() > 0){
                return (Concurso) list.get(0);
        }else
            return null;
        
    }

	public ConcursoAcademia getConcursoAcademia(Integer idAcademia, Integer idConcurso) {
        List list = getEntityManager().createQuery("Select c from ConcursoAcademia c "
                + " Where c.idAcademia.id = " + idAcademia
                + " and c.concurso.id = " + idConcurso).getResultList();
        
        if (list.isEmpty()) return null;
        else return (ConcursoAcademia) list.get(0);
	}

	public ConcursoAcademiaCoreoParticipantes findParticipante(Integer id) {
		List list = getEntityManager().createQuery("Select c from ConcursoAcademiaCoreoParticipantes c "
                + " Where c.idPersona.id = " + id).getResultList();
        if (list.isEmpty()) return null;
        else return (ConcursoAcademiaCoreoParticipantes) list.get(0);
	}

	public void removeParticipante(ConcursoAcademiaCoreoParticipantes part) {
		em.remove(part);
	}

	public void updateCoreografia(ConcursoAcademiaCoreo selected) {
		if (selected.getId() == null)
			em.persist(selected);
		else
			em.merge(selected);
	}
}