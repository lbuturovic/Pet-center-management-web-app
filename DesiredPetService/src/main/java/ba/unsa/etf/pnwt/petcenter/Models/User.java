package ba.unsa.etf.pnwt.petcenter.Models;


import javax.persistence.*;


@Entity
@SequenceGenerator(name = "User_Id_Seq_Gen", sequenceName = "USER_ID_SEQ_GEN", initialValue = 1)
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "User_Id_Seq_Gen")
    private Integer id;

    private String UUID;

    private String email;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "desired_pet_id", referencedColumnName = "id", nullable = true)
    private DesiredPet desiredPet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public DesiredPet getDesiredPet() {
        return desiredPet;
    }

    public void setDesiredPet(DesiredPet desiredPet) {
        this.desiredPet = desiredPet;
    }

    public User() {
    }

    public User(String UUID) {
        this.UUID = UUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
