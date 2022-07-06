package ba.unsa.etf.pnwt.petcenter.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportResponse {
    private String image;
    private Double longitude;
    private Double latitude;
    private String description;
    private Status status;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Pet type;
}
