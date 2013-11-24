package jsonmodel;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import utils.UserDeserializer;
import utils.UserSerializer;

public class JsonNote {

    private Integer id;
    private Integer version;
    private String name;
    private String note;
    private JsonUser owner;

    public JsonNote() {
    }

    public JsonNote(JsonUser owner) {
        this.owner = owner;
    }

    public JsonNote(String name, String note, JsonUser owner) {
        this.name = name;
        this.note = note;
        this.owner = owner;
    }

    @JsonProperty("id")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer nid) {
        this.id = nid;
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return this.version;
    }

    @JsonIgnore //verzi nelze menit, to dela system
    public void setVersion(Integer version) {
        this.version = version;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("note")
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @JsonProperty("owner")
    @JsonSerialize(using = UserSerializer.class)
    public JsonUser getOwner() {
        return this.owner;
    }

    @JsonIgnore //vlastnika nelze menit
    @JsonDeserialize(using = UserDeserializer.class)
    public void setOwner(JsonUser owner) {
        this.owner = owner;
    }
}
