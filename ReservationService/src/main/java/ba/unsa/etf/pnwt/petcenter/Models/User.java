package ba.unsa.etf.pnwt.petcenter.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@SequenceGenerator(name = "User_Id_Seq_Gen", sequenceName = "USER_ID_SEQ_GEN", initialValue = 1)
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "User_Id_Seq_Gen")
    private Integer id;
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)", name = "uuid")
    private UUID uuid;

    private String email;

    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Reservation> reservations;

    public User() {

    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User(UUID uuid, String email, Set<Reservation> reservations) {
        this.uuid = uuid;
        this.email = email;
        this.reservations = reservations;
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
