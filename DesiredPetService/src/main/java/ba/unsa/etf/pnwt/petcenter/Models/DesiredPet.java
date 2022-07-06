package ba.unsa.etf.pnwt.petcenter.Models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class DesiredPet {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;


    @Enumerated(EnumType.STRING)
    private Species species;


    private String race;

    @OneToOne(mappedBy = "desiredPet")
    private User user;

    public DesiredPet() {
    }

    @Enumerated(EnumType.STRING)
    private Age age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
