/**
 * 
 */
package py.com.app.util;

public class FailedException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -3242441348365331668L;

    public FailedException()
    {
        super();
    }
    
    public FailedException(String msg)
    {
        super(msg);
    }
}
