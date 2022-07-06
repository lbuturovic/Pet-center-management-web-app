package ba.unsa.etf.pnwt.petcenter.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;


@Entity
@SequenceGenerator(name = "Center_Id_Seq_Gen", sequenceName = "CENTER_ID_SEQ_GEN", initialValue = 1)
public class Center {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "Center_Id_Seq_Gen")
    private Integer id;

    @NotNull(message = "Capacity must be specified!")
    @Min(value = 0 , message = "Min value for capacity is 0!")
    private Integer capacity = 0;

    @Min(value = 0 , message = "Min value for pet number is 0!")
    private Integer petNo;

    @NotNull(message = "City must be specified!")
    @NotBlank(message = "City is mandatory!")
    private String city;

    @NotNull(message = "Address must be specified!")
    @NotBlank(message = "Address is mandatory!")
    private String address;
    private boolean active;


    private String phoneNumber;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "CENTER_SERVICE",
            joinColumns = @JoinColumn(name = "center_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id")
    )
    private Set<Service> services = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "center")
    private Set<Pet> pets;

    public Center() {
    }

    public Center(Integer capacity, String address, String city, String phoneNumber) {
        this.capacity = capacity;
        this.petNo = 0;
        this.address = address;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.services = new HashSet<>();
        this.active = true;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getPetNo() {
        return petNo;
    }

    public void setPetNo(Integer petNo) {
        this.petNo = petNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
