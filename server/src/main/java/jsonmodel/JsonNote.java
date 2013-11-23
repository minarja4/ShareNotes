package jsonmodel;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

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
    public JsonUser getOwner() {
        return this.owner;
    }

    @JsonIgnore //vlastnika nelze menit
    public void setOwner(JsonUser owner) {
        this.owner = owner;
    }
}
