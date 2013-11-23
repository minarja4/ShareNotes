package resources;

import dao.UserDAO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jsonmodel.JsonToken;
import jsonmodel.JsonUser;
import jsonmodel.Transformer;

@Path("/users/")
public class UserResource {

    /**
     * Dostane username, email a heslo noveho uzivatele a zaregistruje ho.
     *
     * @param json {"username": XY, "email": XY, "password": XY}
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonUser register(JsonUser newUser) {
        UserDAO dao = new UserDAO();
        return Transformer.transform(dao.create(newUser));
    }

    /**
     * Dostane username a heslo registrovaneho uzivatele a vrati token se kterym
     * bude uzivatel dale pracovat pro manipulaci s poznamkami.
     *
     * @param json {"username": XY, "password": XY}
     */
    @POST
    @Path("/login/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonToken login(JsonUser json) {
        return Transformer.userToken(new UserDAO().login(json));
    }
}
