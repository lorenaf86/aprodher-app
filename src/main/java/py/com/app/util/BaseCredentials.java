package py.com.app.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class BaseCredentials
{
    protected String ipAddr;
    protected String username;
    protected String password;
    protected String sessionHash;
    protected Long idUsuario;
    protected Long idEmpleado;

    protected Properties parameters;

    protected List<String> rols;
    
    protected List<String> funcionalidades;
    
    protected String module;

    public void init()
    {
        ipAddr = null;
        username = null;
        password = null;
        sessionHash = null;
        idUsuario = null;
        idEmpleado = null;
        parameters = new Properties();
        rols = new LinkedList<String>();
        funcionalidades = new LinkedList<String>();
        module = null;
    }

    
    public String getIpAddr()
    {
        return ipAddr;
    }

    
    public void setIpAddr(String ipAddr)
    {
        this.ipAddr = ipAddr;
    }

    
    public String getUsername()
    {
        return username;
    }

    
    public void setUsername(String username)
    {
        this.username = username;
    }

    
    public String getPassword()
    {
        return password;
    }

    
    public void setPassword(String password)
    {
        this.password = password;
    }

    public Properties getParameters()
    {
        return parameters;
    }

    
    public void setParameters(Properties parameters)
    {
        this.parameters = parameters;
    }

    
    public void addParamenter(String key, Object value)
    {
        parameters.put(key, value);
    }

    
    public void addRole(String rol)
    {
        rols.add(rol);
    }

    
    public boolean hasRol(String rol)
    {
        return rols.contains(rol);
    }
    
    public boolean hasFunctionality(String functionality)
    {
        return funcionalidades.contains(functionality);
    }

    public List<String> getRols()
    {
        return rols;
    }

    public void setRols(List<String> rols)
    {
        this.rols = rols;
    }
    
    public String getSessionHash()
    {
        return sessionHash;
    }
    
    public void setSessionHash(String sessionHash)
    {
        this.sessionHash = sessionHash;
    }
    
//    @JsonIgnore
    public String getUserInfo()
    {
        return username + " [" + ipAddr + "]";
    }


	public List<String> getFuncionalidades() {
		return funcionalidades;
	}


	public void setFuncionalidades(List<String> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}


	public String getModule() {
		return module;
	}


	public void setModule(String module) {
		this.module = module;
	}


	public Long getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	public Long getIdEmpleado() {
		return idEmpleado;
	}


	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	
}