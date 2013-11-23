package jsonmodel;

import java.util.ArrayList;
import java.util.List;
import model.Note;
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

    public static List<JsonNote> transform(List<Note> notes) {
        List<JsonNote> out = new ArrayList<JsonNote>();
        for (Note note : notes) {
            out.add(transform(note));
        }
        return out;
    }

    public static JsonToken userToken(User user) {
        JsonToken token = new JsonToken();
        token.setToken(user.getToken());
        return token;
    }
}
