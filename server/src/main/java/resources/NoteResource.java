package resources;

import dao.UserDAO;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jsonmodel.JsonNote;
import jsonmodel.JsonToken;
import jsonmodel.JsonUser;
import jsonmodel.Transformer;

@Path("/note/")
public class NoteResource {

    /**
     * Na zaklade hlavicky X-Token v dotazu vrati seznam poznamek patricich
     * prihlasenemu uzivateli.
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JsonNote> list(@HeaderParam("X-Token") String token) {
        return null;
    }

    /**
     * Na zaklade hlavicky X-Token vytvori novou poznamky prihlasenemu
     * uzivateli.
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonNote list(JsonNote newNote, @HeaderParam("X-Token") String token) {
        return null;
    }

    /**
     * Na zaklade hlavicky X-Token ziska jednu konkretni poznamku prihlaseneho
     * uzivatele.
     */
    @GET
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonNote get(@PathParam("noteId") long noteId,
            @HeaderParam("X-Token") String token) {
        return null;
    }
}
