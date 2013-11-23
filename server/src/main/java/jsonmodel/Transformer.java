package jsonmodel;

import model.Note;
import model.User;

public class Transformer {

    public static JsonUser transform(User user) {
        JsonUser json = new JsonUser();
        json.setId(user.getId());
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

    public static JsonToken userToken(User user) {
        JsonToken token = new JsonToken();
        token.setToken(user.getToken());
        return token;
    }
}
