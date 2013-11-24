package utils;

import dao.NoteDAO;
import dao.ShareDAO;
import dao.UserDAO;
import exception.BadRequestException;
import exception.NotAuthorizedException;
import model.Note;
import model.Share;
import model.User;

public class SecurityUtil {

    public static User checkToken(String username, String token) {
        User user = new UserDAO().byUsername(username);
        if (!user.getToken().equals(token)) {
            throw new NotAuthorizedException("Not authorized to perform this action");
        }
        return user;
    }

    public static Note checkNoteOwnership(User owner, int noteId) {
        Note note = new NoteDAO().byId(noteId);
        if (!owner.getId().equals(note.getOwner().getId())) {
            throw new BadRequestException("Requested note doesn't belong to you");
        }
        return note;
    }

    public static Note canReadNote(User user, int noteId) {
        return new ShareDAO().byUserNote(user.getId(), noteId).getNote();
    }

    public static Note canWriteNote(User user, int noteId) {
        Share share = new ShareDAO().byUserNote(user.getId(), noteId);
        if (Boolean.TRUE.equals(share.getReadonly())) {
            throw new BadRequestException("You are not allowed to change the note");
        }
        return share.getNote();
    }
}
