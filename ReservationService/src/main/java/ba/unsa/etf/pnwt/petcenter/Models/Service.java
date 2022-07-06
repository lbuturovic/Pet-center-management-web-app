package ba.unsa.etf.pnwt.petcenter.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


@Entity
@SequenceGenerator(name = "Ser_Id_Seq_Gen", sequenceName = "SER_ID_SEQ_GEN", initialValue = 1)
public class Service {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "Ser_Id_Seq_Gen")
    private Integer id;
    private String  name;
    private String type;
    private Integer duration;
    private Integer price;

    @JsonIgnore
    @OneToMany(mappedBy = "service")

    private Set<Reservation> reservations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
