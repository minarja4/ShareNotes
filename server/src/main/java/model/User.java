package model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "User", catalog = "sharenotes")
public class User implements java.io.Serializable {

    private Integer id;
    private String username;
    private String email;
    private String password;
    private String token;
    private Set<Note> notes;

    public User() {
    }

    public User(String username, String email, String password, String token) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
        this.notes = new HashSet<Note>();
    }

    public User(String username, String email, String password, String token, Set<Note> notes) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.token = token;
        this.notes = notes;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "uid", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer uid) {
        this.id = uid;
    }

    @Column(name = "username", length = 64, nullable = false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "email", length = 100, nullable = false)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", length = 64, nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "token", length = 64, nullable = false)
    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "note")
    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }
}
