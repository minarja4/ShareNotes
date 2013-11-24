package resources;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jsonmodel.JsonNote;
import utils.SecurityUtil;

/**
 * Shared = poznamky ktere jsou sdileny cizimi uzivateli s danym uzivatelem.
 * Tato trida obsahuje metody pro ziskani vsechn poznamek, ktere jsou sdileny s
 * danym uzivatelem, odhlaseni se od sdileni poznamky, vraceni sdileni poznamky
 * a uprava sdilene poznamky.
 */
@Path("/{username}/shared/")
public class SharedResource {

    /**
     * Ziskani vsech cizich poznamek ktere jsou sdileny ostatnimi s uzivatelem
     * {username}.
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JsonNote> shared(@PathParam("username") String username,
            @HeaderParam("X-Token") String token) {
        SecurityUtil.token(username, token);
        return null;
    }

    /**
     * Ziskani konkretni cizi poznamky ktera je sdilena s uzivatelem {username}.
     */
    @GET
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JsonNote> get(@PathParam("username") String username,
            @HeaderParam("X-Token") String token,
            @PathParam("noteId") int noteId) {
        SecurityUtil.token(username, token);
        return null;
    }

    /**
     * Upraveni cizi poznamky ktera je sdilena s uzivatelem {username}.
     * Predpoklad, uzivatel mu pravo danou cizi poznamku upravovat.
     */
    @PUT
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JsonNote> update(@PathParam("username") String username,
            @HeaderParam("X-Token") String token,
            @PathParam("noteId") int noteId) {
        SecurityUtil.token(username, token);
        return null;
    }

    /**
     * Odhlaseni uzivatele {username} od sdileni cizi poznamky. Slouzi k tomu,
     * kdy cizi clovek nasdili poznamku s uzivatelem, ale ten ji odmitne.
     */
    @DELETE
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void signoff(@PathParam("username") String username,
            @HeaderParam("X-Token") String token,
            @PathParam("noteId") int noteId) {
        SecurityUtil.token(username, token);
    }
}
