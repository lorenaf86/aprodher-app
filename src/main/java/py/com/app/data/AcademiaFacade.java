/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.app.data;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import py.com.app.model.Academia;

/**
 *
 * @author Lorena Franco
 */
@Stateless
public class AcademiaFacade extends AbstractFacade<Academia> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AcademiaFacade() {
        super(Academia.class);
    }
    
}
