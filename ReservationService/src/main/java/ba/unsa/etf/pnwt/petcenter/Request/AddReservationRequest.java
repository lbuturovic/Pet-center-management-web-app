package ba.unsa.etf.pnwt.petcenter.Request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddReservationRequest {
    private LocalDateTime timeStart;
    private Integer userId;
    private Integer serviceId;
    private Integer centerId;
}
