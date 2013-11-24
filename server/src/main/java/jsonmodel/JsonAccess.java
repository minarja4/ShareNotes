package jsonmodel;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.UserDeserializer;
import utils.UserSerializer;

public class JsonAccess {

    private JsonUser user;
    private Boolean readonly;

    public JsonAccess() {
    }

    public JsonAccess(JsonUser user, Boolean readonly) {
        this.user = user;
        this.readonly = readonly;
    }

    @JsonProperty("username")
    @JsonSerialize(using = UserSerializer.class)
    public JsonUser getUser() {
        return user;
    }

    @JsonDeserialize(using = UserDeserializer.class)
    public void setUser(JsonUser user) {
        this.user = user;
    }

    @JsonProperty("readonly")
    public Boolean getReadonly() {
        return readonly;
    }

    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }
}
