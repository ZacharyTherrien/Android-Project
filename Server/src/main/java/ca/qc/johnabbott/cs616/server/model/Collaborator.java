package ca.qc.johnabbott.cs616.server.model;

import javax.persistence.*;

/**
 * Collaborator class
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
@Entity
@Table(name = "collaborator")
public class Collaborator {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Note.class)
    @JoinColumn(name = "note")
    private Note note;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user")
    private User user;

    public long getId() {
        return id;
    }

    public Collaborator setId(long id) {
        this.id = id;
        return this;
    }

    public Note getNote() {
        return note;
    }

    public Collaborator setNote(Note note) {
        this.note = note;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Collaborator setUser(User user) {
        this.user = user;
        return this;
    }
}
