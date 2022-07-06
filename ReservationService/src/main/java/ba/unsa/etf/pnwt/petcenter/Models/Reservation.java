package ba.unsa.etf.pnwt.petcenter.Models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@SequenceGenerator(name = "Res_Id_Seq_Gen", sequenceName = "RES_ID_SEQ_GEN", initialValue = 1)
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "Res_Id_Seq_Gen")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CENTER_ID")
    private Center center;

    @ManyToOne
    @JoinColumn(name = "SERVICE_ID")
    private Service service;

    public Integer getId() {
        return id;
    }

    public void setId(Integer reservationId) {
        this.id = reservationId;
    }


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }
}
