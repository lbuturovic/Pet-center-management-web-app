package ba.unsa.etf.pnwt.petcenter.Model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Report {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)")
    private UUID reportID;
    @Lob
    private byte[] image;
    @NotNull(message = "Longitude must not be null")
    @DecimalMax(value = "180.0", message = "Longitude value out of range! Should be between -180 and 180")
    @DecimalMin(value ="-180.0", message = "Longitude value out of range! Should be between -180 and 180")
    private Double longitude;
    @NotNull(message = "Latitude must not be null")
    @DecimalMin(value = "-90.0", message = "Latitude value out of range! Should be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude value out of range! Should be between -90 and 90")
    private Double latitude;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;
    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private Pet type;
    @CreationTimestamp
    LocalDateTime timestamp;
    @UpdateTimestamp
    LocalDateTime updateTimestamp;

    public Report() {
    }

    public Report(byte[] image, Double longitude, Double latitude, String description, Status status) {
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.status = status;
    }

    public UUID getReportID() {
        return reportID;
    }

    public void setReportID(UUID reportID) {
        this.reportID = reportID;
    }

    public byte[] getImagePath() {
        return image;
    }

    public void setImagePath(byte[] image) {
        this.image = image;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public Pet getType() {
        return type;
    }

    public void setType(Pet type) {
        this.type = type;
    }
}
