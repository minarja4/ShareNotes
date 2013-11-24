package utils;

import java.io.IOException;
import jsonmodel.JsonUser;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class UserDeserializer extends JsonDeserializer<JsonUser> {

    @Override
    public JsonUser deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return new JsonUser(jp.getText());
    }
}
