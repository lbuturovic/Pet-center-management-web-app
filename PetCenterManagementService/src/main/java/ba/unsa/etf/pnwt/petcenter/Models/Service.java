package ba.unsa.etf.pnwt.petcenter.Models;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name = "Service_Id_Seq_Gen", sequenceName = "SERVICE_ID_SEQ_GEN", initialValue = 1)
public class Service {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "Service_Id_Seq_Gen")
    private Integer id;

    @NotNull
    @NotBlank(message = "Name is mandatory!")
    private String name;

    private Double price;

    @NotNull
    @Min(value = 0, message = "Invalid value for duration!" )
    private Integer duration;

    @Enumerated(EnumType.STRING)
    private ServiceType type;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE
            },
            mappedBy = "services")
    private Set<Center> centers = new HashSet<>();

    public Service() {
    }

    public Service(String name, Double price, Integer duration, Set<Center> centers) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.centers = centers;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public Set<Center> getCenters() {
        return centers;
    }

    public void setCenters(Set<Center> centers) {
        this.centers = centers;
    }
}
