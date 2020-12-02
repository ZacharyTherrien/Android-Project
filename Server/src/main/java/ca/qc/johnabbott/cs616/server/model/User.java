package ca.qc.johnabbott.cs616.server.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * User class
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = UuidGenerator.generatorName)
    @GenericGenerator(name = UuidGenerator.generatorName, strategy = "ca.qc.johnabbott.cs616.server.model.UuidGenerator")
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "collaborator",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "note")
    )
    private List<Note> notes;

    public String getUuid() {
        return uuid;
    }

    public User setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
