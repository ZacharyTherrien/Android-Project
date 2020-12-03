package ca.qc.johnabbott.cs616.server.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="task")
public class Task {

    @Id
    @GeneratedValue(generator = UuidGenerator.generatorName)
    @GenericGenerator(name = UuidGenerator.generatorName, strategy = "ca.qc.johnabbott.cs616.server.model.UuidGenerator")
    @Column(name = "uuid")
    private String uuid;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @OneToMany(mappedBy = "task")
    private List<Entry> entries;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
