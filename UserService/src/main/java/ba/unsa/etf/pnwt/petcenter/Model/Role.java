package ba.unsa.etf.pnwt.petcenter.Model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_generator")
    @Column(name = "role_id")
    private Integer roleID;
    @NotBlank(message = "Name of role is mandatory")
    private String name;

    /*@ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE
            },
            mappedBy = "roles")
    private List<User> users = new ArrayList<>();*/

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }

   /* public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }*/

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

