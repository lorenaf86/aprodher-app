/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import py.com.app.model.Academia;
import py.com.app.model.ConcursoOrden;
import py.com.app.model.Usuario;

/**
 *
 * @author Lorena Franco
 */
@Stateless
public class OrdenFacade {

    @Inject
    private EntityManager em;

    public List<ConcursoOrden> findAll() {
    	
        return em.createNativeQuery("Select * from concurso_orden where id_concurso in "
            	+ " (Select id from concurso c "
            	+ " Where c.vigente is true Order by c.fecha desc limit 1)"
				+ " order by orden"
        		+ "",ConcursoOrden.class).getResultList();
        
    }
    
    public List<ConcursoOrden> findAll(Integer idUsuario) {
	    Integer id = ((Usuario)em.find(Usuario.class, idUsuario)).getAcademia().getId();
	    Academia academia = em.find(Academia.class, id);
	    
	    return em.createNativeQuery("Select * from concurso_orden"
	    		+ " where id_academia = " + academia.getId()
	    		+ " and id_concurso in "
            	+ " (Select id from concurso c "
            	+ " Where c.vigente is true Order by c.fecha desc limit 1)"
				+ " order by orden"
	    		+ "",ConcursoOrden.class).getResultList();
    }
    
    public void generarOrden() {
    	em.createNativeQuery("ALTER SEQUENCE concurso_orden_seq RESTART WITH 1").executeUpdate();
    	em.flush();
    	
    	em.createNativeQuery("TRUNCATE concurso_orden").executeUpdate();
    	em.flush();

    	String sql = " Insert into concurso_orden (nombre, id_categoria, id_academia, id_modalidad, id_persona, id_tipo_participacion, coreografia,"
    			+ " orden_categoria, orden_modalidad, orden_tipo, id_concurso, valor, cantidad, id_coreografia)";
    	sql += " (Select a.nombre, a.id_categoria";
    	sql += " , b.id_academia, a.id_modalidad, a.id_persona, a.id_tipo_participacion, a.coreografia, cat.orden as orden_categoria";
    	sql += " ,mod.orden as orden_modalidad, tp.orden as orden_tipo, b.id_concurso, tp.valor, a.cantidad, a.id";
    	sql += " from concurso_academia_coreo a";
    	sql += " join concurso_academia b on a.id_concurso_academia = b.id";
    	sql += " join categoria cat on cat.id = a.id_categoria";
    	sql += " join concurso_modalidad mod on mod.id = a.id_modalidad";
    	sql += " join concurso_tipo_participacion tp on tp.id = a.id_tipo_participacion";
    	sql += " where b.id_concurso in ";
    	sql += " (Select id from concurso c ";
    	sql += " Where c.vigente is true Order by c.fecha desc limit 1)";
    	sql += " order by cat.orden, mod.orden,  tp.orden)";
    	
    	em.createNativeQuery(sql).executeUpdate();	
    	em.flush();
    	
    	em.createNativeQuery("Update concurso_orden set orden = id ").executeUpdate();

    }

	public ArrayList findAllTotal() {

	    String sql = " select v.academia, sum(v.cantidad) as cantidad, sum(v.valor) as valor"; 
	    sql += " from v_resumen v group by v.academia";

	    return (ArrayList) em.createNativeQuery(sql).getResultList();
	    
	}

	public ArrayList findAllTotal(int idUsuario) {

	    Integer id = ((Usuario)em.find(Usuario.class, idUsuario)).getAcademia().getId();
	    Academia academia = em.find(Academia.class, id);

	    String sql = " select v.academia, sum(v.cantidad) as cantidad, sum(v.valor) as valor"; 
	    sql += " where v.academia = '" + academia.getNombre() + "'";
	    sql += " from v_resumen v group by v.academia";
		
	    return (ArrayList) em.createNativeQuery(sql).getResultList();
	}

	public void updateOrden(Integer id, int i) {

    	em.createNativeQuery("Update concurso_orden set orden = " + i +
    			" where id = " + id).executeUpdate();
		
	}
    
}
