package exception;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;

public class NotAuthorizedException extends WebApplicationException {

    private static final long serialVersionUID = 1L;
    private String error;

    public NotAuthorizedException(String error) {
        super(Response.status(Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN)
                .entity(error).build());
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
