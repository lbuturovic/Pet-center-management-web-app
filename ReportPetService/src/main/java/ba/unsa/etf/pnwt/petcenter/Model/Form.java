package ba.unsa.etf.pnwt.petcenter.Model;

import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Form {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)")
    private UUID formID;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "report")
    private Report report;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "center")
    //@OnDelete(action = OnDeleteAction.CASCADE) //promijeniti nullable u true
    private Center center;
    private Status status;
    @CreationTimestamp
    LocalDateTime timestamp;
    @UpdateTimestamp
    LocalDateTime updateTimestamp;
    public Form() {
    }

    public Form(Report report, String description, Center center, Status status) {
        this.report = report;
        this.description = description;
        this.center = center;
        this.status = status;
    }

    public UUID getFormID() {
        return formID;
    }

    public void setFormID(UUID formID) {
        this.formID = formID;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
