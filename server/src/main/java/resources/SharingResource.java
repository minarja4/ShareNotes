package resources;

import dao.ShareDAO;
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
import jsonmodel.JsonAccess;
import jsonmodel.Transformer;
import model.Note;
import model.User;
import utils.SecurityUtil;

/**
 * Sharing = poznamky, ktere dany uzivatel sdili s ostatnimi uzivateli. Tato
 * trida obsahuje metody pro vytvoreni sdilenych poznamek, ziskani seznamu
 * poznamek ktere uzivatel sdili s ostatnimi, pridavani lidi se kterymi bude
 * poznamka sdilena a zruseni sdileni poznamky.
 */
@Path("/{username}/sharing/")
public class SharingResource {

    /**
     * Ziskani vsech poznamek, ktere uzivatel {username} nasdilel ostatnim
     * uzivatelum.
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JsonNote> sharing(@PathParam("username") String username,
            @HeaderParam("X-Token") String token) {
        User user = SecurityUtil.checkToken(username, token);
        return Transformer.notesTransform(new ShareDAO().allSharing(user));
    }

    /**
     * Ziskani seznamu uzivatelu a jejich prav, kterym byla nasdilena poznamka
     * {noteId} patrici uzivateli {username}.
     */
    @GET
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JsonAccess> get(@PathParam("username") String username,
            @HeaderParam("X-Token") String token,
            @PathParam("noteId") int noteId) {
        User user = SecurityUtil.checkToken(username, token);
        Note note = SecurityUtil.checkNoteOwnership(user, noteId);
        return Transformer.sharesTransform(new ShareDAO().allAccess(note));
    }

    /**
     * Nasdileni poznamky {noteId} patrici uzivateli {username} vsem uzivatelum
     * vyjmenovanych v seznamu uzivatelu a jejich prav.
     */
    @POST
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JsonAccess> share(@PathParam("username") String username,
            @HeaderParam("X-Token") String token,
            @PathParam("noteId") int noteId,
            List<JsonAccess> list) {
        User user = SecurityUtil.checkToken(username, token);
        SecurityUtil.checkNoteOwnership(user, noteId);
        return Transformer.sharesTransform(new ShareDAO().createAccess(list, noteId));
    }

    /**
     * Aktualizace seznamu uzivatelu a jejich prav, kterym je nasdilena poznamka
     * {noteId} patrici uzivateli {username}.
     */
    @PUT
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<JsonAccess> update(@PathParam("username") String username,
            @HeaderParam("X-Token") String token,
            @PathParam("noteId") int noteId,
            List<JsonAccess> list) {
        User user = SecurityUtil.checkToken(username, token);
        SecurityUtil.checkNoteOwnership(user, noteId);
        return Transformer.sharesTransform(new ShareDAO().updateAccess(list, noteId));
    }

    /**
     * Zruseni sdileni poznamky {noteId} se vsemi uzivateli. Vyuziti: uzivatel
     * se rozhodne, ze poznamku jiz nechce nadale sdilet s nikym jinym.
     */
    @DELETE
    @Path("/{noteId}/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void unshare(@PathParam("username") String username,
            @HeaderParam("X-Token") String token,
            @PathParam("noteId") int noteId) {
        User user = SecurityUtil.checkToken(username, token);
        Note note = SecurityUtil.checkNoteOwnership(user, noteId);
        new ShareDAO().unshare(note);
    }
}
