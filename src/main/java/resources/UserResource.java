package resources;

import dao.UserDAO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.User;

@Path("/user/")
public class UserResource {

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User register(User newDest) {
        UserDAO dao = new UserDAO();
        User user = dao.byId(1);
//        return Transform.transform(destination);
        return user;
    }
}
