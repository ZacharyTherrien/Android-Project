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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    public Date getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(Date startedOn) {
        this.startedOn = startedOn;
    }

    public Date getEndedOn() {
        return endedOn;
    }

    public void setEndedOn(Date endedOn) {
        this.endedOn = endedOn;
    }
}

