package ba.unsa.etf.pnwt.petcenter.Repository;

import ba.unsa.etf.pnwt.petcenter.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
