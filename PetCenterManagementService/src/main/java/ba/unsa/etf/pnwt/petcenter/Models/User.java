package ba.unsa.etf.pnwt.petcenter.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = "User_Id_Seq_Gen", sequenceName = "USER_ID_SEQ_GEN", initialValue = 1)
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "User_Id_Seq_Gen")
    private Integer id;

    private String UUID;

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<Pet> pets;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Center center;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
