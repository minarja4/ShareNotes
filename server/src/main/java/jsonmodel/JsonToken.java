package jsonmodel;

import org.codehaus.jackson.annotate.JsonProperty;

public class JsonToken {

    private String token;

    public JsonToken() {
    }

    public JsonToken(String token) {
        this.token = token;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
