package ba.unsa.etf.pnwt.petcenter.Model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@SequenceGenerator(name = "Center_Id_Seq_Gen", sequenceName = "CENTER_ID_SEQ_GEN", initialValue = 1)
public class Center {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "Center_Id_Seq_Gen")
    private Integer centerID;
    private String name;
    private Integer id; //preuzima se iz mikroservisa pet center mngmnt trenutno
    private boolean active;
    public Center() {
    }

    public Center(String name, Integer id) {
        this.name = name;
        this.id = id;
        this.active = true;
    }

    public Integer getCenterID() {
        return centerID;
    }

    public void setCenterID(Integer centerID) {
        this.centerID = centerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
