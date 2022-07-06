package ba.unsa.etf.pnwt.petcenter.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportRequest {
    @NotBlank(message = "Image is mandatory")
    private String image;
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
    @Column(name="type")
    private Pet type;
}
