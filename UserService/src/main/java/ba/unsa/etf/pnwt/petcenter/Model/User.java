package ba.unsa.etf.pnwt.petcenter.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)", name = "user_id")
    private UUID userID;

    @Column(unique=true)
    @NotBlank(message = "Username cannot be null")
    @Size(min=3, max=30, message = "Username must have at lest 3 characters")
    private String username;

    @NotBlank (message = "Password cannot be null")
    @Size(min=5, message = "Password must have at least 5 characters")
    private String password;

    @NotBlank (message = "First name cannot be null")
    @Size(min=2, max=30, message = "First name must be between 2 and 30 characters long")
    private String firstName;

    @NotBlank (message = "Last name cannot be null")
    @Size(min=2, max=30, message = "Last name must be between 2 and 30 characters long")
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Column(unique=true)
    @NotBlank (message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @CreationTimestamp
    @FutureOrPresent
    LocalDateTime timestamp;
    @UpdateTimestamp
    @FutureOrPresent
    LocalDateTime updateTimestamp;

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void deleteRole(Role role) {
       roles.remove(role);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public List<String> getRolesByName() {
        List<String> roleNames = new ArrayList<>();
        for (Role r: getRoles()) {
            roleNames.add(r.getName());
        }
        return roleNames;
    }

}
