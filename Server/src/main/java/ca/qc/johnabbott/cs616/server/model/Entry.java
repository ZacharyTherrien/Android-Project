package ca.qc.johnabbott.cs616.server.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "entry")
public class Entry {

    @Id
    @GeneratedValue(generator = UuidGenerator.generatorName)
    @GenericGenerator(name = UuidGenerator.generatorName, strategy = "ca.qc.johnabbott.cs616.server.model.UuidGenerator")
    @Column(name = "uuid")
    private String uuid;

    @ManyToOne(targetEntity = Task.class)
    @JoinColumn(name = "task")
    private Task task;

    @Column(name = "name")
    private String name;

    @Column(name = "time")
    private int time;

    @Column(name = "addedon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedOn;

    @Column(name = "startedon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startedOn;

    @Column(name = "endedon")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endedOn;



}

