/**
 * 
 */
package py.com.app.util;

/**
 * @author RSGPTIv2
 * 
 */
public class ValidationException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -8444437995711983390L;

    public ValidationException(String message)
    {
        super(message);
    }

    public ValidationException()
    {
        super();
    }

    public ValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ValidationException(Throwable cause)
    {
        super(cause);
    }
}
