package model;

public class ShareId implements java.io.Serializable {

    private int user;
    private int note;

    public ShareId() {
    }

    public ShareId(int user, int note) {
        this.user = user;
        this.note = note;
    }

    public int getUser() {
        return this.user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getNote() {
        return this.note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof ShareId)) {
            return false;
        }
        ShareId castOther = (ShareId) other;

        return (this.getUser() == castOther.getUser())
                && (this.getNote() == castOther.getNote());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getUser();
        result = 37 * result + this.getNote();
        return result;
    }
}
