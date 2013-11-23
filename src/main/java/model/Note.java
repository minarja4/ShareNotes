package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "Note", catalog = "sharenotes")
public class Note implements java.io.Serializable {

    private Integer id;
    private Integer version;
    private String name;
    private String note;
    private User owner;

    public Note() {
    }

    public Note(User owner) {
        this.owner = owner;
    }

    public Note(String name, String note, User owner) {
        this.name = name;
        this.note = note;
        this.owner = owner;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "nid", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer nid) {
        this.id = nid;
    }

    @Version
    @Column(name = "version")
    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "note", length = 65535)
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner", nullable = false)
    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
