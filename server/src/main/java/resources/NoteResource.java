package resources;

import dao.NoteDAO;
import dao.UserDAO;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jsonmodel.JsonNote;
import jsonmodel.Transformer;
import model.User;

@Path("/{username}/notes/")
public class NoteResource {

    /**
     * Ziskani vsech poznamek patrici uzivateli {username}.
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JsonNote> list(@PathParam("username") String username,
            @HeaderParam("X-Token") String token) {
        User user = checkToken(username, token);
        return Transformer.transform(new NoteDAO().all(user));
    }

    /**
     * Vytvoreni nove poznamky uzivateli {username}.
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonNote create(@PathParam("username") String username,
            JsonNote newNote, @HeaderParam("X-Token") String token) {
        User user = checkToken(username, token);
        return Transformer.transform(new NoteDAO().create(user, newNote));
    }

    /**
     * Ziskani konkretni poznamky patrici uzivateli {username}.
     */
    @GET
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonNote get(@PathParam("username") String username,
            @PathParam("noteId") int noteId, @HeaderParam("X-Token") String token) {
        checkToken(username, token);
        return Transformer.transform(new NoteDAO().byId(noteId));
    }

    /**
     * Upraveni konkretni poznamky patrici uzivateli {username}.
     */
    @PUT
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonNote change(@PathParam("username") String username,
            @PathParam("noteId") int noteId, @HeaderParam("X-Token") String token,
            JsonNote newNote) {
        checkToken(username, token);
        return Transformer.transform(new NoteDAO().update(newNote, noteId));
    }

    /**
     * Odstraneni konkretni poznamky patrici uzivateli {username}.
     */
    @DELETE
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonNote delete(@PathParam("username") String username,
            @PathParam("noteId") int noteId, @HeaderParam("X-Token") String token) {
        checkToken(username, token);
        return Transformer.transform(new NoteDAO().delete(noteId));
    }

    private User checkToken(String username, String token) {
        return new UserDAO().authorized(username, token);
    }
}
