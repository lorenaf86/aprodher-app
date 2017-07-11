package py.com.app.util;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.enterprise.event.Event;
import javax.faces.event.ActionEvent;

import py.com.app.data.AbstractFacade;


/**
 * Represents an abstract shell of to be used as JSF Controller to be used in
 * AJAX-enabled applications. No outcomes will be generated from its methods
 * since handling is designed to be done inside one page.
 */
public abstract class AbstractController<T>
{
    
    protected transient Logger logger;
    private static String entityClassName = null;
    
    private AbstractFacade<T> service;
    private Class<T> itemClass;
    private T selected;
    private List<T> collection;
    private List<T> items;
    private List<T> itemsFiltrado;
    private T savedObject;
    
    protected String action = null;
    protected String actionSubTitle = null;

    private boolean saveCollection = false;
    private boolean fireObserver = false;

    private enum PersistAction
    {
        CREATE, DELETE, UPDATE
    }

    public AbstractController() { }

    public AbstractController(Class<T> itemClass)
    {
        this.itemClass = itemClass;
        entityClassName = AppHelper.fixDTOname(itemClass.getSimpleName());
    }

    public AbstractFacade<T> getService()
    {
        return service;
    }
    
    public void setService(AbstractFacade<T> service)
    {
        this.service = service;
    }

    public T getSelected()
    {
        return selected;
    }

    public void setSelected(T selected)
    {
        this.selected = selected;
    }
    
    public T getSavedObject() {
		return savedObject;
	}

	public void setSavedObject(T savedObject) {
		this.savedObject = savedObject;
	}

	protected void setEmbeddableKeys()
    {
        // Nothing to do if entity does not have any embeddable key.
    }

    ;

    protected void initializeEmbeddableKey()
    {
        // Nothing to do if entity does not have any embeddable key.
    }
    
    /**
     * Returns all items in a List object
     * 
     * @return
     */
    public List<T> getItems()
    {
        if (items == null)
        {
            try
            {
                items = service.findAll();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                JsfUtil.addErrorMessage(e.getMessage());
            }
        }
        return items;
    }
    
    public void updateItems(List<T> l)
    {
        for (T e : l) { 
            updateItem(e);
        }
    }
    
    public void updateItem(T e)
    {
        items.add(e);
    }
    
    protected void clearItems()
    {
        items = new LinkedList<T>();
    }

    public List<T> getItemsFiltrado()
    {
        return itemsFiltrado;
    }

    public void setItemsFiltrado(List<T> itemsFiltrado)
    {
        this.itemsFiltrado = itemsFiltrado;
    }

    
    public void enableSaveCollection()
    {
        saveCollection = true;
    }
    
    public void disableSaveCollection()
    {
        saveCollection = false;
    }
    
    public void enableFireObserver()
    {
        fireObserver = true;
    }    
    
    public void update()
    {
        String msg =  ResourceBundle.getBundle("/Bundle").getString("msg.actualizar");
//        String msg = AppHelper.getBundleMessage("msg.actualizar");//entityClassName + 
        persist(PersistAction.UPDATE, msg);
    }
    
    public void deleteLogical()
    {
//        String msg = AppHelper.getBundleMessage("msg.deleted"); //itemClass.getSimpleName() +
        String msg =  ResourceBundle.getBundle("/Bundle").getString("msg.deleted");
        persist(PersistAction.UPDATE, msg);
        
        if (!isValidationFailed())
        {
            selected = null; // Remove selection
            items = null; // Invalidate list of items to trigger re-query.
        }
        
    }

    public void create()
    {
        String msg =  ResourceBundle.getBundle("/Bundle").getString("msg.guardar");
        persist(PersistAction.CREATE, msg);
        
        if (!isValidationFailed())
        {
            items = null; // Invalidate list of items to trigger re-query.
        }
    }
    
    public void save(ActionEvent event)
    {
        String msg =  ResourceBundle.getBundle("/Bundle").getString("msg.actualizar");
//        String msg = AppHelper.getBundleMessage("msg.updated");//itemClass.getSimpleName()
        persist(PersistAction.UPDATE, msg);
    }

    public void saveNew(ActionEvent event)
    {
        String msg = ResourceBundle.getBundle("/Bundle").getString("msg.created");;
     //   String msg = AppHelper.getBundleMessage("msg.created");//itemClass.getSimpleName()
        persist(PersistAction.CREATE, msg);
        if (!isValidationFailed())
        {
            items = null; // Invalidate list of items to trigger re-query.
        }
    }
    
    public void delete(ActionEvent event)
    {
        String msg =  ResourceBundle.getBundle("/Bundle").getString("msg.deleted");
//        String msg = AppHelper.getBundleMessage("msg.deleted"); //itemClass.getSimpleName() +
        persist(PersistAction.DELETE, msg);
        
        if (!isValidationFailed())
        {
            selected = null; // Remove selection
            items = null; // Invalidate list of items to trigger re-query.
        }
    }
    
    public void createdReturnObject()
    {
        String msg = ResourceBundle.getBundle("/Bundle").getString("msg.guardar");;
        persistReturnObject(PersistAction.CREATE, msg);
        
        if (!isValidationFailed())
        {
            items = null; // Invalidate list of items to trigger re-query.
        }
    }


    public void delete()
    {
        String msg = ResourceBundle.getBundle("/Bundle").getString("msg.eliminar");
        persist(PersistAction.DELETE, msg);
        
        if (!isValidationFailed())
        {
            selected = null; // Remove selection
            items = null; // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage)
    {
        this.setEmbeddableKeys();
        try
        {
            if (persistAction == PersistAction.DELETE)
            {
                if(saveCollection) {
                    service.remove(getCollection());
                } else {
                    service.remove(selected);
                }
            }
            else
            {
                if (persistAction == PersistAction.UPDATE){
                    if(saveCollection) {
                        service.edit(getCollection());
                    } else {
                        service.edit(selected);
                    }
                }else{
                    if(saveCollection) {
                     // service.create(getCollection());
                    } else {
                       service.create(selected);
                    }
                }
            }
            
            if(fireObserver)
            {
                getUpdateListenersEvent().fire(selected);
            }
            
            JsfUtil.addSuccessMessage(successMessage);
            
        }
        catch (EJBException ex)
        {
            displayError(ex.getCause(), ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("msg.error"));
        }
        
    }
    
    private void persistReturnObject(PersistAction persistAction, String successMessage)
    {
        this.setEmbeddableKeys();
        try
        {
            if (persistAction == PersistAction.DELETE)
            {
                if(saveCollection) {
                    service.remove(getCollection());
                } else {
                    service.remove(selected);
                }
            }
            else
            {
                if(saveCollection) {
                    service.edit(getCollection());
                } else {
                   savedObject = (T) service.updateReturnObject(selected);
                }
            }
            
            if(fireObserver)
            {
                getUpdateListenersEvent().fire(selected);
            }
            
            JsfUtil.addSuccessMessage(successMessage);
            
        }
        catch (EJBException ex)
        {
            displayError(ex.getCause(), ex);
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("msg.persistencia"));
        }
        
    }

    }
    /**
     * this method must be override it to get the needed information <br>
     * it is public so you can override it when you need it<br>
     * <br>
     * property fireObserver must be set to <b>true</b> on backingBean's constructor<br>
     */
    public Event<T> getUpdateListenersEvent()
    {
        return null;
    }

    /**
     * this method must be override it to get the needed information <br>
     * it is public so you can override it when you need it<br>
     * <br>
     * property saveCollection must be set to <b>true</b> on backingBean's constructor<br>
     */
    public List<T> getCollection()
    {
        return collection;
    }

    private void displayError(Throwable throwable, Exception e)
    {
        String msg = "";
        Throwable cause = JsfUtil.getRootCause(throwable);
        if (cause != null)
        {
            msg = cause.getLocalizedMessage();
        }
        if (msg.length() > 0)
        {
            JsfUtil.addErrorMessage(msg);
        }
        else
        {
            if((e.getMessage().contains(".")) && 
                    (e.getMessage().split("\\.").length > 2)
            )
            {
                msg = e.getMessage();
            }
            else {
                msg = ResourceBundle.getBundle("/Bundle").getString("msg.persistencia");
                //msg = AppHelper.getBundleMessage("msg.persistencia");
            }
            
            JsfUtil.addErrorMessage(e, msg);
        }
    }

    /**
     * Creates a new instance of an underlying entity and assigns it to Selected
     * property.
     * 
     * @return
     */
    public T prepareCreate()
    {
        T newItem;
        try
        {
            newItem = itemClass.newInstance();
            this.selected = newItem;
            this.savedObject = newItem;
            initializeEmbeddableKey();
            return newItem;
        }
        catch (InstantiationException ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception e) {
        	e.printStackTrace();
		}
        return null;
    }
    
    public T prepareCreate(ActionEvent event)
    {
        T newItem;
        try
        {
            newItem = itemClass.newInstance();
            this.selected = newItem;
            initializeEmbeddableKey();
            return newItem;
        }
        catch (InstantiationException ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isValidationFailed()
    {
        return JsfUtil.isValidationFailed();
    }
    
    public void loadInfo()
    {
    };
    
    public String getActionSubTitle() {
		return actionSubTitle;
	}

	public void setActionSubTitle(String actionSubTitle) {
		this.actionSubTitle = actionSubTitle;
	}
	
    public void initItems()
    {
        items = null;
    }

	public void setCollection(List<T> collection) {
		this.collection = collection;
	}

}