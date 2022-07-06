package ba.unsa.etf.pnwt.petcenter.Models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(name = "Pet_Id_Seq_Gen", sequenceName = "PET_ID_SEQ_GEN", initialValue = 1)
public class Pet {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "Pet_Id_Seq_Gen")
    private Integer id;

    @NotNull(message = "Species must be specified!")
    @Enumerated(EnumType.STRING)
    private Species species;

    @NotBlank(message = "Race is mandatory!")
    private String race;

    @NotBlank(message = "Name is mandatory!")
    private String name;

    @Enumerated(EnumType.STRING)
    private Age age;

    @DecimalMin(value = "0.01", message = "Invalid value for weight!")
    private Float weight;

    private String healthCondition;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Lob
    private byte[] image;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "center_id")
    private Center center;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private User user;


    public Pet() {
    }

    public Pet(Species species, String race, String name, Age age, Float weight, String healthCondition, Gender gender, byte[] image, Status status, Category category, User user, Center center) {
        this.race = race;
        this.species = species;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.healthCondition = healthCondition;
        this.gender = gender;
        this.image = image;
        this.status = status;
        this.category = category;
        this.user = user;
        this.center = center;

    }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
