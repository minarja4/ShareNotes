package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@IdClass(ShareId.class)
@Table(name = "Share", catalog = "sharenotes")
public class Share implements java.io.Serializable {

    private User user;
    private Note note;
    private Boolean readonly;

    public Share() {
    }

    public Share(User user, Note note, Boolean readonly) {
        this.user = user;
        this.note = note;
        this.readonly = readonly;
    }

    public Share(Boolean readonly) {
        this.readonly = readonly;
    }

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "note", nullable = false)
    public Note getNote() {
        return this.note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    @Column(name = "readonly")
    public Boolean getReadonly() {
        return this.readonly;
    }

    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }
}
