package resources;

import com.sun.jersey.api.view.Viewable;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class IndexResource {

    @GET
    @Produces({MediaType.TEXT_HTML})
    @Path("/")
    public Response webPage() {
        return Response.ok(new Viewable("/index"), MediaType.TEXT_HTML)
                .build();
    }
}
