/**
 * 
 */
package py.com.app.util;

/**
 * @author RSGPTIv2
 * 
 */
public class DatabaseException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 4873923532860406538L;
    
    private String errorCode = null;
    
    public DatabaseException()
    {
        super();
    }

    public DatabaseException(String message)
    {
        super(message);
    }
    
    public DatabaseException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }
    

    public String getErrorCode()
    {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
    
}
