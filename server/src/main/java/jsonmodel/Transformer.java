package jsonmodel;

import java.util.ArrayList;
import java.util.List;
import model.Note;
import model.Share;
import model.User;

public class Transformer {

    public static JsonUser transform(User user) {
        JsonUser json = new JsonUser();
        json.setId(user.getId());
        json.setUsername(user.getUsername());
        json.setEmail(user.getEmail());
        json.setPassword(user.getPassword());
        return json;
    }

    public static JsonNote transform(Note note) {
        JsonNote json = new JsonNote();
        json.setId(note.getId());
        json.setName(note.getName());
        json.setNote(note.getNote());
        json.setOwner(transform(note.getOwner()));
        json.setVersion(note.getVersion());
        return json;
    }

    public static List<JsonNote> notesTransform(List<Note> notes) {
        List<JsonNote> out = new ArrayList<JsonNote>();
        for (Note note : notes) {
            out.add(transform(note));
        }
        return out;
    }

    public static List<JsonAccess> sharesTransform(List<Share> shares) {
        List<JsonAccess> out = new ArrayList<JsonAccess>();
        for (Share share : shares) {
            out.add(transform(share));
        }
        return out;
    }

    public static JsonToken userToken(User user) {
        JsonToken token = new JsonToken();
        token.setToken(user.getToken());
        return token;
    }

    public static JsonAccess transform(Share share) {
        JsonAccess json = new JsonAccess();
        json.setUser(transform(share.getUser()));
        json.setReadonly(share.getReadonly());
        return json;
    }

    public static JsonNote sharedTransform(Share shared) {
        JsonNote note = transform(shared.getNote());
        note.setReadonly(shared.getReadonly());
        return note;
    }

    public static List<JsonNote> sharedTransform(List<Share> allShared) {
        List<JsonNote> out = new ArrayList<JsonNote>();
        for (Share shared : allShared) {
            out.add(sharedTransform(shared));
        }
        return out;
    }
}
