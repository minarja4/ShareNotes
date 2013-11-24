package utils;

import dao.NoteDAO;
import dao.UserDAO;
import exception.BadRequestException;
import exception.NotAuthorizedException;
import model.Note;
import model.User;

public class SecurityUtil {

    public static User token(String username, String token) {
        User user = new UserDAO().byUsername(username);
        if (!user.getToken().equals(token)) {
            throw new NotAuthorizedException("Not authorized to perform this action");
        }
        return user;
    }

    public static Note noteOwnership(User owner, int noteId) {
        Note note = new NoteDAO().byId(noteId);
        if (!owner.getId().equals(note.getOwner().getId())) {
            throw new BadRequestException("Requested note doesn't belong to you");
        }
        return note;
    }
}
