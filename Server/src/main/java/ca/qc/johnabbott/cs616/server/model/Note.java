package ca.qc.johnabbott.cs616.server.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Note class
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
@Entity
@Table(name="note")
public class Note {

    @Id
    @GeneratedValue(generator = UuidGenerator.generatorName)
    @GenericGenerator(name = UuidGenerator.generatorName, strategy = "ca.qc.johnabbott.cs616.server.model.UuidGenerator")
    @Column(name = "uuid")
    private String uuid;

    @Column(name="created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name="reminder")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reminder;

    @Column(name="modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="body")
    private String body;

    /*@Column(name="category")
    @Enumerated(EnumType.STRING)
    private Category category;
*/
    @ManyToMany(mappedBy = "notes")
    private List<User> collaborators;


}
