package ba.unsa.etf.pnwt.petcenter.Controller;

import ba.unsa.etf.pnwt.petcenter.Model.Role;
import ba.unsa.etf.pnwt.petcenter.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping(path = "/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/role") //done
    public ResponseEntity<Role> getAllRoles(@RequestParam(required = false) String name) {
        Role role = roleService.getRole(name);
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    @GetMapping("/role/{id}") //done
    public ResponseEntity<Role> getRoleById( @PathVariable("id") Integer id) {
        return new ResponseEntity<>(roleService.getRole(id), HttpStatus.OK);
    }
    @PostMapping("/role") //done
    public ResponseEntity<Role> createRole(@Valid @RequestBody Role role) {
        return new ResponseEntity<>(roleService.addRole(role), HttpStatus.CREATED);
    }
    @PutMapping("/role/{id}") //done
    public ResponseEntity<Role> updateRole(@PathVariable("id") Integer id, @Valid @RequestBody Role role) {
        return new ResponseEntity<>(roleService.updateRole(id, role), HttpStatus.OK);
    }
    @DeleteMapping("/role/{id}") //done
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable("id") Integer id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/role") //done
    public ResponseEntity<HttpStatus> deleteAllRoles() {
        roleService.deleteRole(null);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
