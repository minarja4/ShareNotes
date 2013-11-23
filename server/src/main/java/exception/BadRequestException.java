/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

/**
 *
 * @author lubos
 */
/** Thrown to return a 400 Bad Request response with a list of error messages in the body. */
public class BadRequestException extends WebApplicationException
{
    private static final long serialVersionUID = 1L;
    private String error;
 
    public BadRequestException(String error)
    {
        super(Response.status(Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity(error).build());
        this.error = error;
    }
 
    public String getError()
    {
        return error;
    }
}
