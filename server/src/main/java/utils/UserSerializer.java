package utils;

import java.io.IOException;
import jsonmodel.JsonUser;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class UserSerializer extends JsonSerializer<JsonUser> {

    @Override
    public void serialize(JsonUser t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeString(t.getUsername());
    }
}
