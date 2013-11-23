package jsonmodel;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class JsonUser {

    private Integer id;
    private String email;
    private String password;

    public JsonUser() {
    }

    public JsonUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @JsonProperty("id")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer uid) {
        this.id = uid;
    }

    @JsonProperty("email")
    public String getEmail() {
        return this.email;
    }

    @JsonIgnore //email nelze zmenit
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore //heslo nelze ziskat
    public String getPassword() {
        return this.password;
    }

    @JsonProperty //heslo se da nastavit, ale nelze ho ziskat
    public void setPassword(String password) {
        this.password = password;
    }
}
