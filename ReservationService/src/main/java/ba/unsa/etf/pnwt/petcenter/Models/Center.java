package ba.unsa.etf.pnwt.petcenter.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@SequenceGenerator(name = "Cen_Id_Seq_Gen", sequenceName = "CEN_ID_SEQ_GEN", initialValue = 1)
public class Center {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "Cen_Id_Seq_Gen")
    private Integer id;
    private String name;
    private Integer idMain; //preuzima se iz mikroservisa pet center mngmnt trenutno

    public Center() {
    }

    public Center(String name, Integer idMain) {
        this.name = name;
        this.idMain = idMain;
        this.reservations = null;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "center",  cascade = CascadeType.ALL)
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

    public Integer getIdMain() {
        return idMain;
    }

    public void setIdMain(Integer idMain) {
        this.idMain = idMain;
    }
}
