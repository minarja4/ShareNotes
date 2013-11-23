package exception;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

public class NotFoundException extends WebApplicationException
{
    private static final long serialVersionUID = 2L;
    private String error;
 
    public NotFoundException(String error)
    {
        super(Response.status(Status.NOT_FOUND).type(MediaType.TEXT_PLAIN)
                .entity(error).build());
        this.error = error;
    }
 
    public String getError()
    {
        return error;
    }
}
